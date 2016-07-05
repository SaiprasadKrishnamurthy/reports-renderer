package org.sai.raas.js;

import org.sai.raas.model.SVGProvider;

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