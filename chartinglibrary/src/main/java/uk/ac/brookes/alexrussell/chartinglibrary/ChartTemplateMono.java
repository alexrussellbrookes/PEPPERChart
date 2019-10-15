package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

/**
 * The basic Chart superclass inherited by all chart classes.
 */
public abstract class ChartTemplateMono {
    String type;
    String chartID;
    JSONArray initData;
    int fontSize;
    String xDataName;
    String yDataName;
    boolean update;
    ChartTypes chartType;
    String updateString = "";
    ChartModelMonoAbstract chartModel;

    ChartTemplateMono(ChartModelMonoAbstract chartModel, JSONArray initData, boolean update) {
        this.chartModel = chartModel;
        this.initData = initData;
        this.update = update;
    }

    private String getCMType() {
        return chartModel.getCMType();
    }

    /**
     * Returns the chart's String identifier.
     * @return the chart's String identifier.
     */
    public String getChartID() {
        return chartID;
    }

    String getChartModelAccessors(String var) {
        StringBuilder returnString = new StringBuilder();
        String chartModelID = var + "chartModel";
        returnString.append("var ").append(chartModelID).append(" = ").append(getCMType()).append("();").append("\n");
        returnString.append(getXAccessor(chartModelID, xDataName, getChartType()));
        returnString.append(getGenericAccessor("y", yDataName, chartModelID));
        returnString.append(chartModel.getChartModelSpec(chartModelID));
        returnString.append(getJSFunctionCall(chartModelID, "timeScale", Boolean.toString(getTimeScale(getChartType()))));
        return returnString.toString();
    }

    String getChartModelSpec(String var) {
        return chartModel.getChartModelSpec(var);
    }

    private String getChartSpec(String var) {
        StringBuilder returnString = new StringBuilder();
        String chartModelID = var + "chartModel";
        returnString.append("var ").append(var).append(" = ").append(type).append("(").append(chartModelID).append(");").append("\n");
        if (chartID != null) {
            returnString.append(getJSFunctionCallStringInput(var, "chartID", chartID));
        }
        returnString.append(getMultAxisSpec(var));
        returnString.append(getFontSpec(var));
        returnString.append(getConcreteChartSpec(var));
        return returnString.toString();
    }

    ChartTypes getChartType() {
        return chartType;
    }

    abstract String getConcreteChartSpec(String var);

    /**
     * Gets the chart's font size.
     * @return the font size
     */
    public int getFontSize() {
        return fontSize;
    }

    String getFontSpec(String var) {
        return getJSFunctionCallStringInput(var, "fontSize", Integer.toString(fontSize));
    }

    String getGenericAccessor(String axisType, String dataType, String chartID) {
        return chartID + "." + axisType + "(function(d) { return(d." + dataType + "==null ? null : +d." + dataType + ");});" + "\n";
    }

    String getInitData() {
        return initData.toString();
    }

    String getJSFunctionCall(String var, String functionName, String functionInput) {
        return var + "." + functionName + "(" + functionInput + ");" + "\n";
    }

    String getJSFunctionCallStringInput(String var, String functionName, String functionInput) {
        return var + "." + functionName + "(\"" + functionInput + "\");" + "\n";
    }

    String getJavaScriptSpec(String chartName, String chartModelName, String selectionName) {
        StringBuilder returnString = new StringBuilder();
        returnString.append(getChartModelAccessors(chartName));
        returnString.append("var ").append(selectionName).append(" = d3.select(\"#").append(chartName).append("\");").append("\n").append(getJSFunctionCall(selectionName, "datum", getInitData()));
        returnString.append(getJSFunctionCall(selectionName, "call", chartModelName));
        returnString.append(getChartSpec(chartName));
        returnString.append(getMultChartSpec(chartName));
        returnString.append(getJSFunctionCall(chartName, "draw", ""));
        return returnString.toString();
    }

    abstract String getMultAxisSpec(String chartName);

    abstract String getMultChartSpec(String chartName);

    abstract String getSpecificStyleSpec(String jsVar);

    abstract String getStyleSpec(String jsVar, String chartID);

    boolean getTimeScale(ChartTypes chartType) {
        return chartType == ChartTypes.TIME_SERIES;
    }

    String getUpdateString() {
        return updateString;
    }

    String getXAccessor(String chartID, String dataType, ChartTypes chartType) {
        return chartID + ".x(function(d) { return d." + dataType + ";});" + "\n";
    }

    /**
     * Returns true if the chart is updateable, else returns false.
     * @return true if chart is updateable, else false.
     */
    public boolean isUpdated() {
        return update;
    }

    /**
     * Sets the chart's String identifier. This is used to identify the chart's ChartUpdate object.
     * @param chartID the chart's String identifier.
     */
    public void setChartID(String chartID) {
        this.chartID = chartID;
    }

    /**
     * Sets the chart's font size.
     * @param fontSize the font size
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Sets the height of the chart in pixels.
     * @param height size in pixels
     */
    public void setHeight(int height) {
        chartModel.setHeight(height);
    }

    /**
     * Sets the size of the chart's bottom margin in pixels.
     * @param dimensions size in pixels
     */
    public void setMarginBottom(int dimensions) {
        chartModel.setMarginBottom(dimensions);
    }

    /**
     * Sets the size of the chart's left margin in pixels.
     * @param dimensions size in pixels
     */
    public void setMarginLeft(int dimensions) {
        chartModel.setMarginLeft(dimensions);
    }

    /**
     * Sets the size of the chart's right margin in pixels.
     * @param dimensions size in pixels
     */
    public void setMarginRight(int dimensions) {
        chartModel.setMarginRight(dimensions);
    }

    /**
     * Sets the size of the uppermost margin of the chart in pixels.
     * @param dimensions size in pixels
     */
    public void setMarginTop(int dimensions) {
        chartModel.setMarginTop(dimensions);
    }

    void setUpdateString(ChartUpdate chartUpdate, String data) {
        StringBuilder update = new StringBuilder();
        String chartID = chartUpdate.getChartName();
        String selectionName = chartUpdate.getSelectionName();
        String chartModel = chartUpdate.getChartModel();
        update.append(getJSFunctionCall(selectionName, "datum", data));
        update.append(getJSFunctionCall(selectionName, "call", chartModel));
        update.append(getJSFunctionCall(chartID, "update", ""));
        updateString = update.toString();
    }

    /**
     * Sets the width of the chart in pixels.
     * @param width size in pixels
     */
    public void setWidth(int width) {
        chartModel.setWidth(width);
    }
}
