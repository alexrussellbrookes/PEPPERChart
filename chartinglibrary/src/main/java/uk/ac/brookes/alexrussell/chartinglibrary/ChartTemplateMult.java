package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

abstract class ChartTemplateMult extends ChartModifiable {
    private String xAxisLabel;
    private String yAxisLabel;
    private ChartModelMultTemplate chartModelMult;

    ChartTemplateMult(ChartModelMultTemplate chartModel, JSONArray initData, boolean update) {
        super(chartModel, initData, update);
        chartModelMult = chartModel;
    }

    String getAxisLabelsSpec(String var) {
        StringBuilder returnString = new StringBuilder();
        if (yAxisLabel != null) {
            returnString.append(getJSFunctionCallStringInput(var, "yAxisLabel", yAxisLabel));
        }
        if (xAxisLabel != null) {
            returnString.append(getJSFunctionCallStringInput(var, "xAxisLabel", xAxisLabel));
        }
        return returnString.toString();
    }

    @Override
    String getMultAxisSpec(String chartName) {
        return getAxisLabelsSpec(chartName);
    }

    @Override
    String getMultChartSpec(String chartName) {
        return addMarks(chartName, chartComponents);
    }

    @Override
    String getStyleSpec(String jsVar, String chartID) {
        StringBuilder returnString = new StringBuilder();
        returnString.append(getSpecificStyleSpec(jsVar));
        if (!chartComponents.isEmpty()) {
            returnString.append(ChartUtils.getStyleSpec(jsVar, chartID, "mark", false, ""));
        }
        return returnString.toString();
    }

    @Override
    String getXAccessor(String chartID, String dataType, ChartTypes chartType) {
        StringBuilder returnString = new StringBuilder();
        if (chartType == ChartTypes.TIME_SERIES) {
            returnString.append(chartID).append(".x(function(d) { return parseDate(d.").append(dataType).append(");});\n");
        } else if (chartType == ChartTypes.LINEAR) {
            returnString.append(chartID).append(".x(function(d) { return +d.").append(dataType).append(";});\n");
        } else if (chartType == ChartTypes.CATEGORICAL) {
            returnString.append(chartID).append(".x(function(d) { return d.").append(dataType).append(";});\n");
        }
        return returnString.toString();
    }

    /**
     * Sets the label above the X-Axis.
     * @param xAxisLabel the String to be displayed on the label.
     */
    public void setXAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    /**
     * Sets the limits of the domain for the X-axis data on the chart.
     * @param specArray An array of two doubles. The first double will be set as the minimum limit of the data domain, and the second double will be the maximum limit.
     */
    public void setXDomainSpec(double[] specArray) {
        chartModelMult.setXDomainSpec(specArray);
    }

    /**
     * Sets the label above the Y-Axis.
     * @param yAxisLabel the String to be displayed on the label.
     */
    public void setYAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }

    /**
     * Sets the limits of the domain for the Y-axis data on the chart.
     * @param specArray An array of two doubles. The first double will be set as the minimum limit of the data domain,
     *                  and the second double will be the maximum limit.
     */
    public void setYDomainSpec(double[] specArray) {
        chartModelMult.setYDomainSpec(specArray);
    }

}
