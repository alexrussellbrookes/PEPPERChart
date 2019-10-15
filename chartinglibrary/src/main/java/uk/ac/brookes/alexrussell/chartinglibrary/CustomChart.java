package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

/**
 * Custom chart to which ChartComponent objects are added.
 */
public class CustomChart extends ChartModifiable {
    private String color = "blue";
    private ChartModelBi chartModel;

    /**
     * Constructs CustomChart.
     * @param chartType the data form of the chart.
     * @param chartModel a new ChartModelBi object.
     */
    public CustomChart(ChartTypes chartType, ChartModelBi chartModel) {
        super(chartModel, new JSONArray(), false);
        this.chartType = chartType;
        this.chartModel = chartModel;
        type = "CustomChart";
        fontSize = 28;
    }
    /**
     * Returns a String with the default colour of the chart's components.
     * @return a String which should conform to a valid CSS colour specification.
     */
    public String getColor() {
        return color;
    }

    @Override
    String getConcreteChartSpec(String var) {
        return "";
    }

    @Override
    String getJavaScriptSpec(String chartName, String chartModelName, String selectionName) {
        StringBuilder returnString = new StringBuilder();
        //build the chart
        returnString.append("var ").append(chartName).append(" = ").append(type).append("(); ");
        returnString.append(chartModel.getChartModelSpec(chartName));
        returnString.append(chartName).append(".timeScale(").append(Boolean.toString(getTimeScale(getChartType()))).append(");\n");

        //Add the marks to the chart
        for (ChartComponent chartComponent : chartComponents) {
            returnString.append(chartComponent.createMark(chartName, this));
        }

        //Create the selection and use it to call the chart
        returnString.append("var ").append(selectionName).append(" = d3.select(\"#").append(chartName).append("\");\n");
        returnString.append(selectionName).append(".call(").append(chartName).append(");\n");

        return returnString.toString();
    }

    @Override
    String getMultAxisSpec(String chartName) {
        return "";
    }

    @Override
    String getMultChartSpec(String chartName) {
        return "";
    }

    @Override
    String getSpecificStyleSpec(String jsVar) {
        return "";
    }

    @Override
    String getStyleSpec(String jsVar, String chartID) {
        return ChartUtils.getStyleSpec(jsVar, chartID, "mark", false, "");
    }

    String getUpdateString(String data, String chartName, String selectionName, String chartModel) {
        return "";
    }

    /**
     * Sets the default color of the chart's components. Particular components may take whatever colour the user wishes.
     * @param color a String which must conform to a valid CSS colour specification.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Sets the limits of the domain for the X-axis data on the chart.
     * @param specArray An array of two doubles. The first double will be set as the minimum limit of the data domain,
     *                  and the second double will be the maximum limit.
     */
    public void setXDomainSpec(double[] specArray) {
        chartModel.setXDomainSpec(specArray);
    }

    /**
     * Sets the limits of the domain for the Y-axis data on the chart.
     * @param specArray An array of two doubles. The first double will be set as the minimum limit of the data domain,
     *                  and the second double will be the maximum limit.
     */
    public void setYDomainSpec(double[] specArray) {
        chartModel.setYDomainSpec(specArray);
    }

}
