package uk.ac.brookes.alexrussell.chartinglibrary;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The container class for all the charts.
 */
public class ChartHolder {

    private ArrayList<ChartTemplateMono> chartList = new ArrayList<>();
    private Context context;
    private HashMap<String, ChartUpdate> updateMap = new HashMap<>();

    /**
     * Creates a ChartHolder object to hold the charts within the overall design.
     * @param context Android Context which is used to reference WebView.
     */
    public ChartHolder(Context context) {
        this.context = context;
    }

    /**
     * Adds a chart to the design.
     * @param chart A chart object to be added to the design.
     */
    public void addChart(ChartTemplateMono chart) {
        chartList.add(chart);
    }

    StringBuilder createChartSet() {
        StringBuilder returnString = new StringBuilder();
        String selectionName = "";
        String chartModelName = "";
        int chartNumber = 1;
        for (int i = 0; i < chartList.size(); i++) {
            String chartName = "";
            ChartTemplateMono chart = chartList.get(i);
            if (chart.getChartID() == null) {
                chartName = "chart" + chartNumber;
                chartNumber++;
            } else {
                chartName = chart.getChartID();
            }
            chartModelName = chartName + "chartModel";
            selectionName = chartName + "selection";

            //Initially creates the CSS specifications for the chart
            returnString.append("var styleSheet = document.styleSheets[0]; ").append("\n");
            returnString.append(chart.getStyleSpec("styleSheet", chartName));
            returnString.append("d3.select(\"body\").append(\"div\").attr(\"id\", \"").append(chartName).append("\"); ").append("\n");
            returnString.append(chart.getJavaScriptSpec(chartName, chartModelName, selectionName)).append("\n");

            //Creates a map of ChartUpdate options which provide references to the Chart options. These are referenced by the updating
            //operations
            if (chart.isUpdated()) {
                ChartUpdate chartUpdate = new ChartUpdate(chart, chartName, selectionName, chartModelName);
                updateMap.put(chartName, chartUpdate);
            }
        }
        return returnString;
    }

    /**
     * Returns a ChartUpdate object to handle update operations. See User Instructions.
     * @param key the String identifier of the Chart object to be updated
     * @return  the ChartUpdate object on which the update operations are to be called
     */
    public ChartUpdate getChartUpdate(String key) {
        return (updateMap.get(key));
    }

    Context getContext() {
        return context;
    }
}
