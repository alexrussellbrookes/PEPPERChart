package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;


public class PieChart extends ChartTemplateMono {

    private int offSet;
    private boolean legend = true;
    private boolean label = false;

    /**
     * Creates a pie chart.
     * @param chartModel a new ChartModelMono object.
     * @param initData the chart's data in JSON form. See User Instructions.
     * @param labelName the name of the field which will be used to provide labels for each slice of the pie.
     * @param valueName the name of the field which will determine the size of each slice in the pie.
     * @param update true if the chart is to be updated, else false.
     */
    public PieChart (ChartModelMono chartModel, JSONArray initData, String labelName, String valueName,  boolean update) {
        super(chartModel, initData, update);
        xDataName = labelName;
        yDataName = valueName;
        type = "PieChart";
        fontSize = 28;
        chartType = ChartTypes.LINEAR;
        offSet = 150;
    }

    @Override
    String getMultChartSpec(String chartName) {
        return "";
    }

    @Override
    String getMultAxisSpec(String chartName) {
        return "";
    }

    @Override
    String getSpecificStyleSpec(String jsVar) {
        return ChartUtils.getStyleSpec(jsVar, chartID, "pie", false, "");
    }

    @Override
    String getConcreteChartSpec(String var) {
        StringBuilder sb = new StringBuilder();
        sb.append(getJSFunctionCall(var, "offSet", Integer.toString(offSet)));
        if (!legend) {
            sb.append(getJSFunctionCall(var, "legend", Boolean.toString(legend)));
        }
        if (label) {
            sb.append(getJSFunctionCall(var, "label", Boolean.toString(label)));
        }
        return sb.toString();
    }

    @Override
    String getStyleSpec(String jsVar, String chartID) {
        return getSpecificStyleSpec(jsVar);
    }


    /**
     * Sets the length by which the labels are offset from the centre of each slice.
     * @param offSet the length in pixel of the offset.
     */
    public void setLabelOffSet(int offSet) {
        if (offSet>=0) {
            this.offSet = offSet;
        }
    }

    /**
     * Sets whether labels are to be written on each segment of the pie. Default is false.
     * @param labelOn
     */
    public void setLabel(boolean labelOn){
        label = labelOn;
    }

    /**
     * Sets whether a legend is to be used to display what each segment on the pie represents. Default is true.
     * @param legendOn
     */
    public void setLegend(boolean legendOn){
        legend = legendOn;
    }
}
