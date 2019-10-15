package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

public class LineChart extends LineChartTemplate {
    boolean dataPoints;
    double[] targetLines;
    int animationType;

    /**
     * Constructs LineChart.
     * @param chartModel a new ChartModelBi object.
     * @param initData the chart's data in JSON form. See User Instructions.
     * @param xDataType the name of the field for the X-data values in the JSON array.
     * @param yDataType the name of the field for the Y-data values in the JSON array.
     * @param timeScale true if the chart displays time series data, else false.
     * @param update true if the chart is to be updated, else false.
     */
    public LineChart(ChartModelBi chartModel, JSONArray initData, String xDataType, String yDataType, boolean timeScale, boolean update) {
        super(chartModel, initData, xDataType, yDataType, timeScale, update);
        type = "LineGraph";
        dataPoints = false;
        zoom = false;
        fontSize = 28;
        strokeColor = "#FF00FF";
        targetLines = new double[0];
        animationType = ChartUtils.getAnimationNumber(AnimationType.GENERAL);
    }

    @Override
    String getConcreteChartSpec(String var) {
        StringBuilder returnString = new StringBuilder();
        returnString.append(getLineChartSpec(var));
        returnString.append(var + ".dataPoints(" + dataPoints + "); " + "\n");
        for (double targetLine : targetLines) {
            returnString.append(var + ".targetLine(" + targetLine + "); " + "\n");
        }
        return returnString.toString();
    }

    boolean getDataPoints() {
        return dataPoints;
    }

    @Override
    String getSpecificStyleSpec(String jsVar) {
        return ChartUtils.getStyleSpec(jsVar, chartID, "line", dataPoints, "");
    }

    public void setAnimationType(AnimationType type) {
        animationType = ChartUtils.getAnimationNumber(type);
    }

    /**
     * Sets whether the chart is to display data points.
     * @param dataPoints true if the chart is to display data points, else false.
     */
    public void setDataPoints(boolean dataPoints) {
        this.dataPoints = dataPoints;
    }

    /**
     * Sets horizonal target lines on the chart.
     * @param targetLines An array of doubles, each specifying a Y-value for the horizontal line.
     */
    public void setTargetLines(double[] targetLines) {
        this.targetLines = targetLines;
    }

    @Override
    void setUpdateString(ChartUpdate chartUpdate, String data) {
        StringBuilder update = new StringBuilder();
        String chartID = chartUpdate.getChartName();
        String selectionName = chartUpdate.getSelectionName();
        String chartModel = chartUpdate.getChartModel();
        update.append(chartID + ".updateMode(" + animationType + ");\n");
        update.append(getJSFunctionCall(selectionName, "datum", data));
        update.append(getJSFunctionCall(selectionName, "call", chartModel));
        update.append(getJSFunctionCall(chartID, "update", ""));
        updateString = update.toString();
    }

}
