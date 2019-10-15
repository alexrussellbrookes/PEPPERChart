package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

public class ScatterPlot extends LineChart {

    public ScatterPlot (ChartModelBi chartModel, JSONArray initData, String xDataType, String yDataType, boolean timeScale, boolean update) {
        super(chartModel, initData, xDataType, yDataType, timeScale, update);
        type = "ScatterPlot";
        dataPoints = true;
    }

    public void setDataPoints(boolean dataPoints){}

    @Override
    String getSpecificStyleSpec(String jsVar) {
        return ChartUtils.getStyleSpec(jsVar, chartID, "scatter", true, strokeColor);
    }

}
