package uk.ac.brookes.alexrussell.chartinglibrary;

import org.json.JSONArray;

/**
 * BubbleChart modified to allow specific side bars with text.
 */

public class PepperBubbleChart extends BubbleChart {
    private String extraText = null;
    private String sumText = null;
    private String unitText = null;
    private boolean sideBar = false;
    private boolean rectDisplay = false;

    public PepperBubbleChart(ChartModelBi chartModel, JSONArray initData, String xDataType, String yDataType, String zDataType, boolean timeScale, boolean update) {
        super(chartModel, initData, xDataType, yDataType, zDataType, timeScale, update);
        type = "PepperBubbleChart";
    }

    @Override
    String getConcreteChartSpec(String var) {
        StringBuilder returnString = new StringBuilder();
        if (rectDisplay) {
            returnString.append(getJSFunctionCall(var, "rectDisplay", Boolean.toString(rectDisplay)));
        }
        if (extraText != null) {
            returnString.append(getJSFunctionCallStringInput(var, "totalText", extraText));
        }
        if (sumText != null) {
            returnString.append(getJSFunctionCallStringInput(var, "sumText", sumText));
        }
        if (unitText != null) {
            returnString.append(getJSFunctionCallStringInput(var, "unitText", unitText));
        }
        returnString.append(getJSFunctionCall(var, "sideBar", Boolean.toString(sideBar)));
        return returnString.toString();
    }

    /**
     * Sets whether the bubbles are to be displayed as rectangles instead. Default is false.
     * @param rectDisplay true if the bubbles are to be displayed as rectangles, else false.
     */
    public void setRectDisplay(boolean rectDisplay) {
        this.rectDisplay = rectDisplay;
    }

    /**
     * Sets whether a side bar is to be displayed to the right of the chart. Default is false.
     * @param sideBar true if a side bar is to be displayed, else false.
     */
    public void setSideBar(boolean sideBar) {
        this.sideBar = sideBar;
    }

    /**
     * Sets the label for the display to be attached to the sum of all the bubble values.
     * @param sumText the label for the sum display.
     */
    public void setSumText(String sumText) {
        this.sumText = sumText;
    }


    /**
     * Sets any additional text to be added to the sidebar label.
     * @param extraText the extra text to be added to the sidebar label.
     */
    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }

    /**
     * Sets the units of the total value to be displayed on the side bar.
     * @param unitText the unit of the total value on the side bar.
     */
    public void setUnitText(String unitText) {
        this.unitText = unitText;
    }
}
