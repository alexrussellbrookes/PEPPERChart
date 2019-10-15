package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * Point for time series data.
 */
public class ChartPointTime implements ChartPoint {
    private String x;
    private double y;

    /**
     * A new point for time series data.
     * @param x the time-date value for the X axis.
     * @param y the numerical Y value.
     */
    public ChartPointTime (String x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getX () {
        return x;
    }

    @Override
    public double getY () {
        return y;
    }

}
