package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * ChartModel for bivariate data.
 */
public class ChartModelBi extends ChartModelMultTemplate {
    int ticks;
    private double[] yDomainSpec;
    private double[] xDomainSpec;

    /**
     * Constructs a ChartModelBi object to be supplied to charts making use of tuple data.
     */
    public ChartModelBi() {
        super();
        ticks = 4;
        yDomainSpec = new double[0];
        xDomainSpec = new double[0];
        type = "ChartModel2Var";
    }

    String getChartModelBiSpec(String var) {
        String returnString = "";
        returnString += var + ".ticks(" + getTicks() + "); ";
        if (getYDomainSpec().length == 2) {
            double[] domain = getYDomainSpec();
            returnString += var + ".yDomainSpec([" + domain[0] + ", " + domain[1] + "]); ";
        }
        if (getXDomainSpec().length == 2) {
            double[] domain = getXDomainSpec();
            returnString += var + ".xDomainSpec([" + domain[0] + ", " + domain[1] + "]); ";
        }
        return returnString;
    }

    @Override
    String getExtraChartModelSpec(String var) {
        return getChartModelBiSpec(var);
    }

    int getTicks() {
        return ticks;
    }

    double[] getXDomainSpec() {
        return xDomainSpec;
    }

    double[] getYDomainSpec() {
        return yDomainSpec;
    }

    void setTicks(int ticks) {
        this.ticks = ticks;
    }

    @Override
    void setXDomainSpec(double[] xDomainSpec) {
        this.xDomainSpec = xDomainSpec;
    }

    @Override
    void setYDomainSpec(double[] yDomainSpec) {
        this.yDomainSpec = yDomainSpec;
    }

}
