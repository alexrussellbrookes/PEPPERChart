package uk.ac.brookes.alexrussell.chartinglibrary;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A SVG path to be added to a chart. The default type is a straight multiline connecting all the points in the path.
 * The curve type can, however, be modified using the setCurveType() method.
 */
public class ChartPath extends ChartComponent {
    private ArrayList<ChartPoint> chartPoints;
    private String strokeColor;
    private String curveType;
    private ChartTypes chartType;

    /**
     * Creates a new ChartPath object.
     *
     * @param strokeColor sets the path's colour. It should conform to a valid CSS colour specification.
     * @param chartType   sets the data type which is displayed by the path.
     */
    public ChartPath(String strokeColor, ChartTypes chartType) {
        chartPoints = new ArrayList<>();
        this.strokeColor = strokeColor;
        this.chartType = chartType;
    }

    /**
     * Adds a point to the path. When the path is displayed it will draw a stroke connecting the points.
     *
     * @param chartPoint point to be added to the path.
     * @throws IllegalArgumentException at run-time if the data type of the point does not match the data type of the path.
     */
    public void addPoint(ChartPoint chartPoint) {
        if (isValidPointType(chartPoint)) {
            chartPoints.add(chartPoint);
        } else {
            throw new IllegalArgumentException("ChartPoint objects in ChartPath must be of specified type in constructor!");
        }
    }

    @Override
    String createMark(String chartName, ChartModifiable chart) {
        StringBuilder returnString = new StringBuilder();
        String markRef = chartName + "mark";
        String dataArray = "dataArray" + chart.getArrayAssignment();
        returnString.append("\nvar pathObject = {}; ").append("\n");
        returnString.append("var ").append(dataArray).append(" = ").append(getDataArray().toString()).append("; \n");
        if (chartType == ChartTypes.TIME_SERIES && chart instanceof CustomChart) {
            returnString.append("var minTime = d3.min(").append(dataArray).append(", function (d) {return ").append(getXAccessor())
                    .append(";});").append("\n");
            returnString.append("var maxTime = d3.max(").append(dataArray).append(", function (d) {return ").append(getXAccessor())
                    .append(";});").append("\n");
            returnString.append(chartName).append(".xDomainSpec([minTime, maxTime]);").append("\n");
        }
        returnString.append("\npathObject.create = function(xScale, yScale, focus) {").append("\n").append("this.xScale = xScale;\n")
                .append("this.yScale = yScale;\n");
        returnString.append("var path = d3.line();\n");
        returnString.append(getXValueAssignment());
        returnString.append(".y(function (d) {return yScale(+d.yValue)})\n");
        if (curveType != null) {
            returnString.append(".curve(").append(curveType).append(")");
        }
        returnString.append(";\nthis.path = path;\nvar selection = focus.append(\"path\")\n.datum(").append(dataArray)
                .append(")\n").append("     .attr(\"fill\", \"none\")\n").append("     .attr(\"class\", \"").append(markRef).append("\")\n")
                .append("     .attr(\"stroke\", \"").append(strokeColor).append("\")\n").append("     .attr(\"d\", path);\n")
                .append(" this.selection = selection;\n").append("};");
        returnString.append("\npathObject.refresh = function() {\n").append("var xScale = this.xScale; \n")
                .append("var yScale = this.yScale;\n")
                .append("var path = this.path;\n").append("this.selection.attr(\"d\", path); };\n\n");
        returnString.append(chartName).append(".addCreateItem(pathObject);\n");
        return returnString.toString();
    }

    ChartTypes getChartType() {
        return chartType;
    }

    JSONArray getDataArray() {
        JSONArray array = new JSONArray();
        try {
            for (ChartPoint chartPoint : chartPoints) {
                JSONObject o = new JSONObject();
                o.put("xValue", chartPoint.getX());
                o.put("yValue", chartPoint.getY());
                array.put(o);
            }
        } catch (Exception e) {
            Log.v("tag", e.getMessage());
        }
        return array;
    }

    private String getXAccessor() {
        if (chartType == ChartTypes.TIME_SERIES) {
            return "parseDate(d.xValue)";
        } else if (chartType == ChartTypes.CATEGORICAL) {
            return "d.xValue";
        } else {
            return "+d.xValue";
        }

    }

    private String getXValueAssignment() {
        String returnString = "";
        if (chartType == ChartTypes.CATEGORICAL) {
            returnString = "path.x(function (d) {return xScale(" + getXAccessor() + ") + xScale.bandwidth()/2;})\n";
        } else {
            returnString = "path.x(function (d) {return xScale(" + getXAccessor() + ");})\n";
        }
        return returnString;
    }

    private boolean isValidPointType(ChartPoint chartPoint) {
        Class pointClass = chartPoint.getClass();
        if (pointClass == ChartPointTime.class && chartType == ChartTypes.TIME_SERIES) {
            return true;
        } else if (pointClass == ChartPointCat.class && chartType == ChartTypes.CATEGORICAL) {
            return true;
        } else if (pointClass == ChartPointLin.class && chartType == ChartTypes.LINEAR) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the curve type of the path. The options in the CurveType enum reflect the D3 curve types. See <a href="https://github.com/d3/d3-shape#curves">https://github.com/d3/d3-shape#curves</a>
     *
     * @param curveType options for the curve type in the CurveType enum.
     */
    public void setCurveType(CurveType curveType) {
        this.curveType = ChartUtils.getCurveType(curveType);
    }

    /**
     * Sets the stroke colour of the path.
     *
     * @param strokeColor this should conform to a valid CSS colour specification
     */
    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

}

