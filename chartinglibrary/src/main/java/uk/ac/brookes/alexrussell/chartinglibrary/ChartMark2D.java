package uk.ac.brookes.alexrussell.chartinglibrary;

abstract class ChartMark2D extends ChartComponent {
    ChartPoint chartPoint;
    private String fillColor;
    private String strokeColor = null;
    private int strokeWidth = 0;

    ChartMark2D(String fillColor, ChartPoint chartPoint) {
        this.fillColor = fillColor;
        this.chartPoint = chartPoint;
    }

    @Override
    String createMark(String chartName, ChartModifiable chart) {
        String type = chartName + "mark";
        String objectName = getObjectName();
        String objectType = getObjectType();
        String xAtr = getXAttribute();
        String yAtr = getYAttribute();
        String dimName = getDimensionName();
        double dimValue = getDimensionValue();
        StringBuilder returnString = new StringBuilder();
        returnString.append("\nvar ").append(objectName).append(" = {};").append("\n").append(objectName).append(".create = function(xScale, yScale, focus) {").append("\n").append("this.xScale = xScale;\n").append("this.yScale = yScale;\n");
        returnString.append("var markSelect = focus.append(\"").append(objectType).append("\")\n").append(".attr(\"class\", \"").append(type).append("\")").append("\n").append(".attr(\"fill\", \"").append(fillColor).append("\")").append("\n");
        if (strokeColor != null) {
            returnString.append(".attr(\"stroke\", \"").append(strokeColor).append("\")\n");
            returnString.append(".attr(\"stroke-width\", \"").append(strokeWidth).append("\")\n");
        }
        if (chartPoint instanceof ChartPointCat) {
            returnString.append(".attr(\"").append(xAtr).append("\", xScale(\"").append(getXAccessor((String) chartPoint.getX())).append("\") + xScale.bandwidth()/2)").append("\n");
        } else {
            returnString.append(".attr(\"").append(xAtr).append("\", xScale(").append(getXAccessor((String) chartPoint.getX())).append("))").append("\n");
        }
        returnString.append(".attr(\"").append(yAtr).append("\", yScale(").append(chartPoint.getY()).append("))").append("\n");
        if (this instanceof ChartRect || this instanceof ChartCircle) {
            returnString.append(".attr(\"").append(dimName).append("\", \"").append(dimValue).append("\")").append("\n");
        }
        returnString.append(getAdditionalSpec());
        returnString.append(";\n");
        returnString.append("this.selection = markSelect;\n").append("};\n\n").append(objectName).append(".refresh = function() {\n");

        if (chartPoint instanceof ChartPointCat) {
            returnString.append("this.selection.attr(\"").append(xAtr).append("\", this.xScale(\"").append(chartPoint.getX()).append("\") + this.xScale.bandwidth()/2)").append("\n");
        } else {
            returnString.append("this.selection.attr(\"").append(xAtr).append("\", this.xScale(").append(getXAccessor((String) chartPoint.getX())).append(")) ").append("\n");
        }
        returnString.append(".attr(\"").append(yAtr).append("\", this.yScale(").append(chartPoint.getY()).append(")); ").append("};\n\n").append(chartName).append(".addCreateItem(").append(objectName).append(");\n");
        return returnString.toString();
    }

    abstract String getAdditionalSpec();

    ChartPoint getChartPoint() {
        return chartPoint;
    }

    abstract String getDimensionName();

    abstract double getDimensionValue();

    abstract String getObjectName();

    abstract String getObjectType();

    String getXAccessor(String value) {
        if (chartPoint instanceof ChartPointTime) {
            return "parseDate(\"" + value + "\")";
        } else {
            return value;
        }
    }

    abstract String getXAttribute();

    abstract String getYAttribute();

    /**
     * Sets the colour and width of the chart mark.
     * @param strokeColor a String which must conform to a valid CSS colour specification.
     * @param strokeWidth the width of the stroke.
     */
    public void setStroke(String strokeColor, int strokeWidth) {
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

}
