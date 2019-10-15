package uk.ac.brookes.alexrussell.chartinglibrary;

import android.util.Log;

/**
 * Allows the user to specify update operations on a chart.
 */
public class ChartUpdate {
    private String selectionName;
    private String chartModel;
    private ChartTemplateMono chart;
    private String chartName;

    ChartUpdate(ChartTemplateMono chart, String chartName, String selectionName, String chartModel) {
        this.chart = chart;
        this.chartName = chartName;
        this.selectionName = selectionName;
        this.chartModel = chartModel;
    }

    /**
     * Supplies the new data set to the chart to be loaded.
     * @param updateString String of JSON data. This must be the complete data set to be displayed and must be in the same form as the data
     *                     originally supplied to the chart.
     */
    public void setUpdateString (String updateString) {
        chart.setUpdateString(this, updateString);
    }

    String getUpdateString() {
        return chart.getUpdateString();
    }

    /**
     * Sets the animation type used by the chart. This ONLY applies to line charts, area charts and scatter plots.
     * @param type Choose from options in the AnimationType enum. See User Instructions.
     */
    public void setAnimationType(AnimationType type) {
        if (chart instanceof LineChart) {
             LineChart line = (LineChart) chart;
             line.setAnimationType(type);
        }
    }

    /**
     * Returns the String identifier of the chart linked to the ChartUpdate object.
     * @return the chart's name.
     */
    public String getChartName() {return chartName;}

    String getSelectionName() {
        return selectionName;
    }

    String getChartModel() {
        return chartModel;
    }


}
