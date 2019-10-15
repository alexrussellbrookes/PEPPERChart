package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * Axis object for use with CustomChart.
 */
public class ChartAxis extends ChartComponent {
    private AxisType axisType;

    /**
     * Constructs a new ChartAxis object for use with a CustomChart.
     * @param axisType one of the options from the AxisType enum.
     */
    public ChartAxis (AxisType axisType) {
        this.axisType = axisType;
    }

    @Override
    String createMark(String chartName, ChartModifiable chart) {
        StringBuilder returnString = new StringBuilder();
        returnString.append("var axisObject = {}; ").append("\n").append("axisObject.create = function(xScale, yScale, focus) {").append("\n")
                .append("var markSelect = focus.append(\"g\")\n");
        if (axisType == AxisType.X_AXIS) {
            returnString.append(" .attr(\"class\", \"xAxis\")").append("\n");
            returnString.append(".attr(\"transform\", \"translate(0,\" + ").append(chartName).append(".marginedHeight() + \")\") ");
            returnString.append(".call(").append(chartName).append(".xAxis()) ");
        }
        else if (axisType == AxisType.Y_AXIS) {
            returnString.append(" .attr(\"class\", \"yAxis\") ").append("\n");
            returnString.append(".call(").append(chartName).append(".yAxis()) ");
        }
        returnString.append(".attr(\"font-size\", \"").append(chart.getFontSize()).append("\"); ");
        returnString.append("this.selection = markSelect;\n").append("};\n").append("axisObject.refresh = function() {\n");
        if (axisType == AxisType.X_AXIS) {
            returnString.append("this.selection.call(").append(chartName).append(".xAxis());};\n");
        }
        else if (axisType == AxisType.Y_AXIS) {
            returnString.append("this.selection.call(").append(chartName).append(".yAxis());};\n");
        }
        returnString.append(chartName).append(".addCreateItem(axisObject);\n");
        return returnString.toString();
    }
}


