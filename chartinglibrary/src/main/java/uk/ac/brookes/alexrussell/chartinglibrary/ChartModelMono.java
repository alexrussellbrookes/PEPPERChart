package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * ChartModel for proportional data. Used in construction of PieChart.
 */
public class ChartModelMono extends ChartModelMonoAbstract {
    int height;
    int width;
    int marginTop;
    int marginBottom;
    int marginRight;
    int marginLeft;
    int ticks;
    String type = "ChartModel2Var";

    /**
     * Creates a new ChartModelMono object for use with the constructor of the PieChart class.
     */
    public ChartModelMono() {
        height = 500;
        width = 960;
        marginTop = 40;
        marginBottom = 110;
        marginRight = 70;
        marginLeft = 70;
        ticks = 4;
    }

    @Override
    String getCMType() {
        return type;
    }

    @Override
    String getChartModelSpec(String var) {
        String returnString = "";
        returnString += var + ".height(" + getHeight() + "); ";
        returnString += var + ".width(" + getWidth() + "); ";
        returnString += var + ".mTop(" + getMarginTop() + "); ";
        returnString += var + ".mBottom(" + getMarginBottom() + "); ";
        returnString += var + ".mLeft(" + getMarginLeft() + "); ";
        returnString += var + ".mRight(" + getMarginRight() + "); ";
        returnString += getExtraChartModelSpec(var);
        return returnString;
    }

    String getExtraChartModelSpec(String var) {
        return "";
    }

    int getHeight() {
        return height;
    }

    int getMarginBottom() {
        return marginBottom;
    }

    int getMarginLeft() {
        return marginLeft;
    }

    int getMarginRight() {
        return marginRight;
    }

    int getMarginTop() {
        return marginTop;
    }

    int getWidth() {
        return width;
    }

    @Override
    void setHeight(int height) {
        this.height = height;
    }

    @Override
    void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    @Override
    void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    @Override
    void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    @Override
    void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    @Override
    void setWidth(int width) {
        this.width = width;
    }
}
