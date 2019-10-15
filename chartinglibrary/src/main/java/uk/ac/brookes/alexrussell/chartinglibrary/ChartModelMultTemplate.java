package uk.ac.brookes.alexrussell.chartinglibrary;

abstract class ChartModelMultTemplate extends ChartModelMono {
    ChartModelMultTemplate() {
        super();
    }

    abstract void setXDomainSpec(double[] domain);

    abstract void setYDomainSpec(double[] domain);
}
