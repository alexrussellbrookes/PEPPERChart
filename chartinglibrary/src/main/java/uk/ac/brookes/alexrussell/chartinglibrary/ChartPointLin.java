package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * Point for linear data.
 */
public class ChartPointLin implements ChartPoint {
    private double x;
    private double y;

    /**
     * A new point for linear data.
     * @param x the numerical x value.
     * @param y the numerical y value.
     */
    public ChartPointLin(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

}
