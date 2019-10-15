package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * ChartModel used for multivariate data. Used in construction of BarChart.
 */
public class ChartModelMult extends ChartModelBi {
    private boolean categoricalScale;
    private String xDataName;
    private String[] yVariables;

    /**
     * Creates a new ChartModelMult object to be supplied to the BarChart constructor.
     * @param xDataName
     * @param yVariables
     */
    public ChartModelMult(String xDataName, String[] yVariables) {
        super();
        categoricalScale = false;
        this.xDataName = xDataName;
        this.yVariables = yVariables;
        type = "ChartModelMultVar";
    }

    boolean getCategoricalScale() {
        return categoricalScale;
    }

    @Override
    String getExtraChartModelSpec(String var) {
        StringBuilder returnString = new StringBuilder();
        returnString.append(getChartModelBiSpec(var));
        returnString.append(var).append(".ordinalScale(").append(getCategoricalScale()).append("); ");
        return returnString.toString();
    }

    String getXKey() {
        return xDataName;
    }

    String[] getYVariables() {
        return yVariables;
    }

    void setCategoricalScale(boolean categoricalScale) {
        this.categoricalScale = categoricalScale;
    }
}
