package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

public class BarChart extends ChartTemplateMult {

    private boolean barLabels = true;
    private boolean legend = false;
    private ChartModelMult chartMultModel;

    /**
     * Constructs a new Bar Chart.
     * @param chartMultModel a new ChartModelMult object.
     * @param initData the chart's data in JSON form. See User Instructions.
     * @param update true if the chart is to be updated, else false.
     */
    public BarChart(ChartModelMult chartMultModel, JSONArray initData, boolean update) {
        super(chartMultModel, initData, update);
        this.chartMultModel = chartMultModel;
        type = "BarChart";
        chartType = ChartTypes.CATEGORICAL;
        fontSize = 28;
        chartMultModel.setCategoricalScale(true);
    }

    @Override
    String getChartModelAccessors(String var) {
        String chartModelID = var + "chartModel";
        StringBuilder returnString = new StringBuilder();
        returnString.append("var ").append(chartModelID).append(" = ").append(chartMultModel.getCMType()).append("(\"").append(chartMultModel.getXKey()).append("\", [");
        String[] yVariables = chartMultModel.getYVariables();
        int i;
        for (i = 0; i < yVariables.length - 1; i++) {
            returnString.append("\"").append(yVariables[i]).append("\", ");
        }
        returnString.append("\"").append(yVariables[i]).append("\"]);\n");
        returnString.append(getXAccessor(chartModelID, chartMultModel.getXKey(), chartType));
        returnString.append(getJSFunctionCall(chartModelID, "ordinalScale", "true"));
        returnString.append(getChartModelSpec(chartModelID));
        return returnString.toString();
    }

    @Override
    String getConcreteChartSpec(String var) {
        return (var + ".barLabels(\"" + barLabels + "\");\n"
        + var + ".legend(" + legend + ");\n");
    }

    @Override
    String getSpecificStyleSpec(String jsVar) {
        return ChartUtils.getStyleSpec(jsVar, chartID, "bar", false, "");
    }

    /**
     * Sets whether text labels are added to the bars.
     * @param barLabels true for labels to be added, else false.
     */
    public void setBarLabels(boolean barLabels) {
        this.barLabels = barLabels;
    }

    /**
     * Sets whether a legend is to be added to the chart. Default is false.
     * @param legend
     */
    public void setLegend(boolean legend) {
        this.legend = legend;
    }
}
