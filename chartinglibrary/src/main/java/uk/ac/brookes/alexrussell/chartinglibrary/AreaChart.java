package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

public class AreaChart extends LineChart {

    /**
     * Constructs a new Area Chart.
     * @param chartModel a new ChartModelBi object.
     * @param initData the chart's data in JSON form. See User Instructions.
     * @param xDataType the name of the field for the X-data values in the JSON array.
     * @param yDataType the name of the field for the Y-data values in the JSON array.
     * @param timeScale true if the chart displays time series data, else false.
     * @param update true if the chart is to be updated, else false.
     */
    public AreaChart(ChartModelBi chartModel, JSONArray initData, String xDataType, String yDataType, boolean timeScale, boolean update) {
        super(chartModel, initData, xDataType, yDataType, timeScale, update);
        type = "AreaChart";
    }

    @Override
    String getSpecificStyleSpec(String jsVar) {
        return ChartUtils.getStyleSpec(jsVar, chartID, "area", getDataPoints(), "");
    }
}
