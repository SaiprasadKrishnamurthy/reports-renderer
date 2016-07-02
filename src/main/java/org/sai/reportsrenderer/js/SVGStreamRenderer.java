package org.sai.reportsrenderer.js;

import org.sai.reportsrenderer.model.SVGProvider;

import java.io.IOException;

public class SVGStreamRenderer implements SVGProvider {

    public final SVGProvider svgProvider;

    public SVGStreamRenderer(final SVGProvider svgProvider) {
        this.svgProvider = svgProvider;
    }

    @Override
    public String getSVG(final String generalOptions, final String chartOptions) throws IOException {
        return svgProvider.getSVG(generalOptions, chartOptions);
    }
}