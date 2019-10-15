package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * Point for categorical data.
 */
public class ChartPointCat extends ChartPointTime {
    /**
     * A new point for categorical data.
     * @param x the category for the X value.
     * @param y the numerical Y value.
     */
    public ChartPointCat(String x, double y) {
        super(x, y);
    }
}
