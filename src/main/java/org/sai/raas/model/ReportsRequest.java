package org.sai.raas.model;

import lombok.Data;

import java.util.Map;

/**
 * Created by saipkri on 05/07/16.
 */
@Data
public class ReportsRequest {
    private String reportKey;
    private ReportOutputType reportOutputType;
    private Map<String, Object> data;
    private PieChartSettings pieChartSettings;
}
