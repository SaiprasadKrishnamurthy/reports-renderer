package org.sai.raas.model;

import java.io.IOException;

/**
 * Created by saikris on 02/07/2016.
 */
public interface SVGProvider {
    String getSVG(String generalOptions, String chartOptions) throws IOException;
}
