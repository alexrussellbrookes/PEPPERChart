package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * A custom mark (such as a circle, rectangle, text item) or an axis to be added to a chart.
 */
public abstract class ChartComponent {
    abstract String createMark(String chartName, ChartModifiable chart);
}
