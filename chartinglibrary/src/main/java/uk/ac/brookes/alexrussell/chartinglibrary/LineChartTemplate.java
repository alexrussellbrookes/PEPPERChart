package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

abstract class LineChartTemplate extends ChartTemplateMult {
    boolean zoom;
    String strokeColor;
    private String curveType;
    private String tooltipLabelX;
    private String tooltipLabelY;
    private String tooltipTimeFormat;
    private String contextDateFormat;

    LineChartTemplate(ChartModelMultTemplate chartModel, JSONArray initData, String xDataType, String yDataType, boolean timeScale,
                      boolean update) {
        super(chartModel, initData, update);
        this.xDataName = xDataType;
        this.yDataName = yDataType;
        chartType = timeScale ? ChartTypes.TIME_SERIES : ChartTypes.LINEAR;
    }

    /**
     * Returns a String with the colour of the chart.
     * @return a String which should conform to a valid CSS colour specification.
     */
    public String getColor() {
        return strokeColor;
    }

    String getLineChartSpec(String var) {
        StringBuilder returnString = new StringBuilder();
        returnString.append(var).append(".zoomOn(").append(zoom).append("); ").append("\n");
        if (strokeColor != null) {
            returnString.append(var).append(".strokeColor(\"").append(strokeColor).append("\");\n");
        }
        if (curveType != null) {
            returnString.append(var).append(".curveType(").append(curveType).append(");\n");
        }
        if (contextDateFormat != null) {
            returnString.append(var).append(".contextTimeFormat(\"").append(contextDateFormat).append("\");\n");
        }
        if (tooltipLabelX != null) {
            returnString.append(var).append(".tooltipLabelX(\"").append(tooltipLabelX).append("\");\n");
        }
        if (tooltipLabelY != null) {
            returnString.append(var).append(".tooltipLabelY(\"").append(tooltipLabelY).append("\");\n");
        }
        if (tooltipTimeFormat != null) {
            returnString.append(var).append(".tooltipTimeFormat(\"").append(tooltipTimeFormat).append("\");\n");
        }
        return returnString.toString();
    }

    /**
     * Returns true if the chart has been set to zoomable, else returns false.
     * @return true if the chart has been set to zoomable, else false.
     */
    public boolean getZoom() {
        return zoom;
    }

    /**
     * Sets the format in which dates are displayed on the X-axis of the context area in a zoomable chart.
     * @param contextDateFormat This should follow the date-time format of the D3 library.
     *                          See <a href="https://github.com/d3/d3-time-format">https://github.com/d3/d3-time-format</a>
     */
    public void setContextDateFormat(String contextDateFormat) {
        this.contextDateFormat = contextDateFormat;
    }

    /**
     * Sets the curve type to be applied to the line on the chart.
     * @param curveType one of the options from the CurveType enum.
     */
    public void setCurveType(CurveType curveType) {
        this.curveType = ChartUtils.getCurveType(curveType);
    }

    /**
     * Sets the label to be placed before the X-value in the tooltip popup of a data point.
     * @param tooltipLabelX the label to be placed before the X-value in the tooltip popup.
     */
    public void setTooltipLabelX(String tooltipLabelX) {
        this.tooltipLabelX = tooltipLabelX;
    }

    /**
     * Sets the label to be placed before the Y-value in the tooltip popup of a data point.
     * @param tooltipLabelY the label to be placed before the Y-value in the tooltip popup.
     */
    public void setTooltipLabelY(String tooltipLabelY) {
        this.tooltipLabelY = tooltipLabelY;
    }

    /**
     * Sets the format in which dates are displayed in the tooltip popups for the data points on a time series chart.
     * @param tooltipTimeFormat This should follow the date-time format of the D3 library.
     *                          See <a href="https://github.com/d3/d3-time-format">https://github.com/d3/d3-time-format</a>
     */
    public void setTooltipTimeFormat(String tooltipTimeFormat) {
        this.tooltipTimeFormat = tooltipTimeFormat;
    }

    /**
     * Sets whether the chart is zoomable.
     * @param zoom true for zoomable, else false.
     */
    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }

}
