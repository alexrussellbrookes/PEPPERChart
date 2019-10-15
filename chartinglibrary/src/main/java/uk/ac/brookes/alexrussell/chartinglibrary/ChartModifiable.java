package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A chart to which chart components may be added. Excludes the pie chart.
 */
public abstract class ChartModifiable extends ChartTemplateMono {

    ArrayList<ChartComponent> chartComponents;
    private Integer arrayCount = 1;

    ChartModifiable(ChartModelMonoAbstract chartModel, JSONArray initData, boolean update) {
        super(chartModel, initData, update);
        chartComponents = new ArrayList<>();
    }

    /**
     * Adds a custom chartComponent to the chart.
     * @param chartComponent the component to be added to the chart.
     */
    public void addChartComponent(ChartComponent chartComponent) {
        if (ChartUtils.isValidDataForm(chartComponent, chartType)) {
            chartComponents.add(chartComponent);
        } else {
            throw new IllegalArgumentException("Chart marks must use the same data type as the chart!");
        }
    }

    String addMarks(String var, ArrayList<ChartComponent> chartComponents) {
        StringBuilder returnString = new StringBuilder();
        //Add the marks to the chart
        for (ChartComponent chartComponent : chartComponents) {
            returnString.append(chartComponent.createMark(var, getChart()));
        }
        return returnString.toString();
    }

    int getArrayAssignment() {
        int returnCount;
        returnCount = arrayCount;
        arrayCount++;
        return returnCount;
    }

    ChartModifiable getChart() {
        return this;
    }
}
