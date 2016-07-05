package org.sai.raas.js;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.*;
import org.sai.raas.model.SVGProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikris on 02/07/2016.
 */
public class JavascriptRenderer implements SVGProvider {

    @Override
    public String getSVG(final String generalOptions, final String chartOptions) throws IOException {
        Object call;

        call = getContextFactory().call(new ContextAction() {
            @Override
            public Object run(Context cx) {
                final Initiator initiator = new Initiator(cx).init();
                return initiator.execute();
            }

            class Initiator {
                Initiator(Context context) {
                    this.cx = context;
                }

                Initiator init() {
                    if (SCRIPTABLE == null)
                        createScriptable();
                    initContext();
                    return this;
                }

                Object execute() {
                    return callJavascript(generalOptions, chartOptions);
                }

                void createScriptable() {

                    cx.setLanguageVersion(Context.VERSION_1_6);

                    SCRIPTABLE = cx.initStandardObjects();

                    cx.setOptimizationLevel(-1);// bypass the 64k limit // interpretive mode
                    attachJs("env.rhino-1.2.js");
                    cx.setOptimizationLevel(9);
                    attachJs("jquery-1.4.3.min.js");
                    attachJs("highcharts-2.1.2.src.js");
                    attachJs("exporting-2.1.2.src.js");
                    attachJs("svg-renderer-highcharts-2.1.2.js");
                    attachJs("add-BBox.js");
                    attachJs("formatWrapper.js");
                }

                @SuppressWarnings("deprecation")
                private void attachJs(String jsFileName) {
                    InputStream in = null;
                    InputStreamReader reader = null;
                    try {
                        in = JavascriptRenderer.class.getClassLoader().getResourceAsStream("js/" + jsFileName);
                        if (in == null)
                            throw new RuntimeException("cannot find js file : " + jsFileName);

                        reader = new InputStreamReader(in);
                        if (scripts == null)
                            scripts = new ArrayList<>();
                        scripts.add(cx.compileReader(SCRIPTABLE, reader, jsFileName, 1, null));
                    } catch (IOException e) {
                        throw new RuntimeException("cannot load js file : " + jsFileName, e);
                    } finally {
                        IOUtils.closeQuietly(in);
                        IOUtils.closeQuietly(reader);
                    }
                }

                void initContext() {
                    cx.setLanguageVersion(Context.VERSION_1_6);
                    cx.setOptimizationLevel(-1);// bypass the 64k limit // interpretive mode

                    for (Script s : scripts) {
                        s.exec(cx, SCRIPTABLE);
                    }
                }

                private final Context cx;
            }
        });

        if (call == null) {
            throw new IllegalStateException("problem during svg generation");
        }

        return call.toString().substring(call.toString().indexOf("<svg"), call.toString().indexOf("</div>"));
    }

    private static ContextFactory getContextFactory() {
        return org.mozilla.javascript.tools.shell.Main.shellContextFactory;
    }

    protected Scriptable SCRIPTABLE;
    private List<Script> scripts;

    protected Object callJavascript(final String generalOptions, final String chartOptions) {
        return ScriptableObject.callMethod(null, SCRIPTABLE, "renderSVGFromJson", new Object[]{'(' + generalOptions + ')', '(' + chartOptions + ')'});
    }
}
