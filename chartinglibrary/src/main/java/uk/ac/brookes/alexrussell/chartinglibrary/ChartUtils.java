package uk.ac.brookes.alexrussell.chartinglibrary;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Convenience functions for chart creation.
 */
public class ChartUtils {

    static int getAnimationNumber(AnimationType type) {
        if (type == AnimationType.GENERAL) {
            return 3;
        } else if (type == AnimationType.FADE) {
            return 2;
        } else {
            return 1;
        }
    }

    static String getCurveType(CurveType curveType) {
        String returnString = "";
        switch (curveType) {
            case CURVE_STEP:
                returnString = "d3.curveStep";
                break;
            case CURVE_BASIS:
                returnString = "d3.curveBasis";
                break;
            case CURVE_BUNDLE:
                returnString = "d3.curveBundle";
                break;
            case CURVE_LINEAR:
                returnString = "d3.curveLinear";
                break;
            case CURVE_NATURAL:
                returnString = "d3.curveNatural";
                break;
            case CURVE_CARDINAL:
                returnString = "d3.curveCardinal";
                break;
            case CURVE_BASIS_OPEN:
                returnString = "d3.curveBasisOpen";
                break;
            case CURVE_MONOTONE_X:
                returnString = "d3.curveMonotoneX";
                break;
            case CURVE_MONOTONE_Y:
                returnString = "d3.curveMonotoneY";
                break;
            case CURVE_STEP_AFTER:
                returnString = "d3.curveStepAfter";
                break;
            case CURVE_CATMULL_ROM:
                returnString = "d3.curveCatmullRom";
                break;
            case CURVE_STEP_BEFORE:
                returnString = "d3.curveStepBefore";
                break;
            case CURVE_BASIS_CLOSED:
                returnString = "d3.curveBasisClosed";
                break;
            case CURVE_CARDINAL_OPEN:
                returnString = "d3.curveCardinalClosed";
                break;
            case CURVE_LINEAR_CLOSED:
                returnString = "d3.curveLinearClosed";
                break;
            case CURVE_CARDINAL_CLOSED:
                returnString = "d3.curveCardinalClosed";
                break;
            case CURVE_CATMULL_ROM_OPEN:
                returnString = "d3.curveCatmullRomOpen";
                break;
            case CURVE_CATMULL_ROM_CLOSED:
                returnString = "d3.curveCatmullRomClosed";
                break;
        }
        return returnString;
    }

    /**
     * A convenience method to create a JSONObject for a time-date value.
     *
     * @param dateName the field name to be given to the time-date value.
     * @param dateTime date-time value represented as a long from a Java Date object.
     * @return a JSONObject to be combined with a numerical value and its identifier to form a tuple
     * @throws JSONException
     */
    public static JSONObject getJSONDate(String dateName, long dateTime) throws JSONException {
        JSONObject jObj = new JSONObject();
        Calendar cal = Calendar.getInstance();
        Date date = new Date(dateTime);
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int minutes = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String dateString = day + "-" + month + "-" + year + " " + hour + ":" + minutes;
        jObj.put(dateName, dateString);
        return jObj;
    }

    /**
     * A convenience method to create a JSONObject for a date-time and number tuple. For use with time series data.
     *
     * @param dateObject JSONObject created with the getJSONDate() method. This represents the date-time value.
     * @param valueName  the field name to be given to the numerical value.
     * @param value      the numerical value in the tuple.
     * @return a JSONObject to be inserted within a JSONArray
     * @throws JSONException
     */
    public static JSONObject getJSONDateTuple(JSONObject dateObject, String valueName, double value) throws JSONException {
        dateObject.put(valueName, value);
        return dateObject;
    }

    static String getStyleSpec(String jsVar, String chartID, String objType, boolean dataPoints, String fill) {
        StringBuilder returnString = new StringBuilder();
        String clip = chartID + "Clip";
        returnString.append(jsVar).append(".insertRule(\".").append(chartID).append(objType).append(" {clip-path: url(#").append(clip)
        .append(");}\", 0);\n");
        if (dataPoints) {
            if (!fill.isEmpty()) {
                returnString.append(jsVar).append(".insertRule(\".").append(chartID).append("dot { fill: ").append(fill)
                        .append("; stroke-width: 1.5px; clip-path: url(#").append(clip).append(");}\", 0);\n");
            } else {
                returnString.append(jsVar).append(".insertRule(\".").append(chartID)
                        .append("dot { fill: white; stroke-width: 1.5px; clip-path: url(#").append(clip).append(");}\", 0);\n");
            }
            returnString.append(jsVar).append(".insertRule(\".").append(chartID)
                    .append("invisible { fill: white; clip-path: url(#").append(clip).append("); opacity: 0;}\", 0);\n");
            returnString.append(jsVar).append(".insertRule(\"div.").append(chartID)
                    .append("tooltip { position: absolute; text-align: center; width: 180px; height: 60px; padding: 4px; font: 24px sans-serif; background: lightsteelblue; border: 0px; border-radius:10px; pointer-events: none;}\", 0);\n");
        }
        return returnString.toString();
    }

    static boolean isValidDataForm(ChartComponent chartComponent, ChartTypes chartType) {
        boolean accept = false;
        if (chartComponent instanceof ChartAxis) {
            accept = true;
        } else if (chartComponent instanceof ChartMark2D) {
            ChartMark2D chartMark2D = (ChartMark2D) chartComponent;
            if (isValidDataForm(chartMark2D.getChartPoint(), chartType)) {
                accept = true;
            }
        } else if (chartComponent instanceof ChartPath) {
            ChartPath chartPath = (ChartPath) chartComponent;
            if (chartPath.getChartType() == chartType) {
                accept = true;
            }
        }
        return accept;
    }

    private static boolean isValidDataForm(ChartPoint chartPoint, ChartTypes chartType) {
        if (chartPoint instanceof ChartPointTime && chartType == ChartTypes.TIME_SERIES) {
            return true;
        } else if (chartPoint instanceof ChartPointLin && chartType == ChartTypes.LINEAR) {
            return true;
        } else if (chartPoint instanceof ChartPointCat && chartType == ChartTypes.CATEGORICAL) {
            return true;
        } else return false;
    }

}
