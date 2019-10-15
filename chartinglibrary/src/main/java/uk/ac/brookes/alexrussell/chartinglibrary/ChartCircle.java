package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * Custom circle to be added to a chart.
 */
public class ChartCircle extends ChartMark2D {

    private double radius;

    /**
     * Constructs a new ChartCircle to be added as a custom component to a chart.
     * @param color a String which must conform to a valid CSS colour specification.
     * @param chartPoint a ChartPoint object with the coordinates of the centre of the circle.
     * @param radius the radius of the circle.
     */
    public ChartCircle (String color, ChartPoint chartPoint, double radius) {
        super(color, chartPoint);
        this.radius = radius;
    }

    @Override
    String getObjectName() {
        return "circleObject";
    }

    @Override
    String getObjectType() {return "circle";}

    @Override
    String getDimensionName() {
        return "r";
    }

    @Override
    double getDimensionValue() {
        return radius;
    }

    @Override
    String getXAttribute() {
        return "cx";
    }

    @Override
    String getYAttribute() {
        return "cy";
    }

    @Override
    String getAdditionalSpec() {
        return "";
    }

}
