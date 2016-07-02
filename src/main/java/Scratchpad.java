import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.sai.reportsrenderer.js.JavascriptRenderer;
import org.sai.reportsrenderer.js.SVGStreamRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * Created by saikris on 02/07/2016.
 */
public class Scratchpad {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            final int index = i;
            Runnable r = () -> {
                try {
                    String svg = new SVGStreamRenderer(new JavascriptRenderer()).getSVG(jsonifyDefaultGeneralOptions(), createJsonColumnBasic());
                    System.out.println(svg);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    new PNGTranscoder().transcode(new TranscoderInput(new ByteArrayInputStream(svg.getBytes())), new TranscoderOutput(out));
                    IOUtils.write(out.toByteArray(), new FileOutputStream(index + ".png"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            };
            r.run();
        }
    }

    public static String jsonifyDefaultGeneralOptions() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ 'global':{'useUTC':false}}");
        return sb.toString();
    }

    private static String createJsonColumnBasic() throws Exception {
        System.out.println(IOUtils.toString(Scratchpad.class.getClassLoader().getResourceAsStream("templates/simple/pie.js")));
        return IOUtils.toString(Scratchpad.class.getClassLoader().getResourceAsStream("templates/simple/pie.js"));
    }
}
