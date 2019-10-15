package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

public class BubbleChart extends ChartTemplateMult {
    private String bubbleColor = "#FF00FF";
    private boolean yAligned = true;
    private boolean yTicks = true;
    private boolean bubbleProp = true;
    private boolean textFill = false;

    private ChartModelBi cm2Var;
    private String xDataType;
    private String yDataType;
    private String zDataType;
    private String label = null;

    /**
     * Constructs a new Bubble Chart.
     * @param chartModel a new ChartModelBi object.
     * @param initData the chart's data in JSON form. See User Instructions.
     * @param xDataType the name of the field for the X-data values in the JSON array.
     * @param yDataType the name of the field for the Y-data values in the JSON array.
     * @param zDataType the name of the field for the values to be encoded by the radius of the bubbles.
     * @param timeScale true if the chart displays time series data, else false.
     * @param update true if the chart is to be updated, else false.
     */
    public BubbleChart(ChartModelBi chartModel, JSONArray initData, String xDataType, String yDataType, String zDataType, boolean timeScale, boolean update) {
        super(chartModel, initData, update);
        cm2Var = chartModel;
        type = "BubbleChart";
        chartType = timeScale ? ChartTypes.TIME_SERIES : ChartTypes.LINEAR;
        fontSize = 28;
        this.xDataType = xDataType;
        this.yDataType = yDataType;
        this.zDataType = zDataType;
    }

    /**
     * Returns the colour of the bubbles.
     * @return a String which should conform to a valid CSS colour specification.
     */
    public String getColor() {
        return bubbleColor;
    }

    @Override
    String getConcreteChartSpec(String var) {
        return "";
    }

    @Override
    String getJavaScriptSpec(String chartName, String chartModelName, String selectionName) {
        StringBuilder returnString = new StringBuilder();
        returnString.append("var ").append(chartName).append(" = ").append(type).append("(); ").append("\n");
        returnString.append(getXAccessor(chartName, xDataType, chartType));
        returnString.append(getGenericAccessor("y", yDataType, chartName));
        returnString.append(getGenericAccessor("z", zDataType, chartName));
        if (label != null) {
            returnString.append(chartName).append(".label(function(d) { return(d.").append(label).append(");}); ").append("\n");
        }
        if (chartID != null) {
            returnString.append(chartName).append(".bubbleID(\"").append(chartID).append("\"); ").append("\n");
        }
        if (chartType == ChartTypes.LINEAR) {
            returnString.append(chartName).append(".timeScale(false);\n");
        }
        returnString.append(getJSFunctionCall(chartName, "textFill", Boolean.toString(textFill)));
        returnString.append(getConcreteChartSpec(chartName));
        returnString.append(getChartModelSpec(chartName));
        returnString.append(getFontSpec(chartName));
        returnString.append(getAxisLabelsSpec(chartName));

        if (bubbleColor != null) {
            returnString.append(getJSFunctionCallStringInput(chartName, "bubbleColor", bubbleColor));
        }
        returnString.append(getJSFunctionCall(chartName, "yAligned", Boolean.toString(yAligned)));
        returnString.append(getJSFunctionCall(chartName, "yTicks", Boolean.toString(yTicks)));
        returnString.append(getJSFunctionCall(chartName, "bubbleProp", Boolean.toString(bubbleProp)));

        if (cm2Var.getYDomainSpec().length == 2) {
            double[] domain = cm2Var.getYDomainSpec();
            returnString.append(chartName).append(".yDomainSpec([").append(domain[0]).append(", ").append(domain[1]).append("]); ").append("\n");
        }
        if (cm2Var.getXDomainSpec().length == 2) {
            double[] domain = cm2Var.getXDomainSpec();
            returnString.append(chartName).append(".xDomainSpec([").append(domain[0]).append(", ").append(domain[1]).append("]); ").append("\n");
        }

        returnString.append(addMarks(chartID, chartComponents));

        returnString.append("var ").append(selectionName).append(" = d3.select(\"#").append(chartName).append("\"); ").append("\n");
        returnString.append(selectionName).append(".datum(").append(getInitData()).append("); ").append("\n");
        returnString.append(selectionName).append(".call(").append(chartName).append("); ").append("\n");

        //Log.v("tag", returnString.toString());
        return returnString.toString();
    }

    @Override
    String getSpecificStyleSpec(String jsVar) {
        return ChartUtils.getStyleSpec(jsVar, chartID, "bubble", false, "");
    }

    /**
     * Sets the colour of the bubbles.
     * @param bubbleColor a String which must conform to a valid CSS colour specification.
     */
    public void setBubbleColor(String bubbleColor) {
        this.bubbleColor = bubbleColor;
    }

    /**
     * Supplies the name of the field from the JSONArray from which the label text will be taken.
     * @param label the name of the field in the JSONArray from which the label text will be taken.
     */
    public void setBubbleLabel(String label) {
        this.label = label;
    }

    /**
     * Sets whether the size of the bubbles is to be proportional to the value of the Z-value. Default is true.
     * @param bubbleProp true to make the size of the bubbles proportional to Z-value, else false.
     */
    public void setBubbleProp(boolean bubbleProp) {
        this.bubbleProp = bubbleProp;
    }

    /**
     * Sets whether text labels are to be added to the centre of the bubbles. Default is false.
     * @param textFill true if labels are to be added, else false.
     */
    public void setTextFill(boolean textFill) {
        this.textFill = textFill;
    }


    @Override
    void setUpdateString(ChartUpdate chartUpdate, String data) {
        updateString = chartUpdate.getChartName() + ".update(" + data + ");" + "\n";
    }

    /**
     * Sets whether the bubbles are to be aligned with their Y-value coordinate. Default is true
     * @param yAligned true if the bubbles are to be aligned with their Y-value coordinate. Else false.
     */
    public void setYAligned(boolean yAligned) {
        this.yAligned = yAligned;
    }

    /**
     * Sets whether the Y-Axis is to have ticks added to it. Default is true.
     * @param yTicks true if ticks are to be added to Y-Axis. Else false.
     */
    public void setYTicks(boolean yTicks) {
        this.yTicks = yTicks;
    }
}
