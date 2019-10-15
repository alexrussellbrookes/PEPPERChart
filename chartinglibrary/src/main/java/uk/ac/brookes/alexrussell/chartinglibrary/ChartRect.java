package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * Custom rectangle.
 */
public class ChartRect extends ChartMark2D {

    private double width;

    /**
     * Creates a new rectangle to be added to a chart.
     * @param color the rectangle's colour. The String should conform to a valid CSS colour specification.
     * @param chartPoint the upper-left point of the rectangle.
     * @param width the width of the rectangle.
     */
    public ChartRect(String color, ChartPoint chartPoint, double width) {
        super(color, chartPoint);
        this.width = width;
    }

    @Override
    String getAdditionalSpec() {
        return ".attr(\"height\", yScale(" + chartPoint.getY() + "))";
    }

    @Override
    String getDimensionName() {
        return "width";
    }

    @Override
    double getDimensionValue() {
        return width;
    }

    @Override
    String getObjectName() {
        return "rectObject";
    }

    String getObjectType() {
        return "rect";
    }

    @Override
    String getXAttribute() {
        return "x";
    }

    @Override
    String getYAttribute() {
        return "y";
    }
}
