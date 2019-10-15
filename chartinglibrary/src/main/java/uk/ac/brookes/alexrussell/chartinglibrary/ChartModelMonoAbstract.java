package uk.ac.brookes.alexrussell.chartinglibrary;

abstract class ChartModelMonoAbstract {
    abstract String getChartModelSpec (String var);
    abstract String getCMType();
    abstract void setHeight(int height);
    abstract void setWidth(int width);
    abstract void setMarginTop(int dimensions);
    abstract void setMarginRight(int dimensions);
    abstract void setMarginLeft(int dimensions);
    abstract void setMarginBottom (int margin);
}
