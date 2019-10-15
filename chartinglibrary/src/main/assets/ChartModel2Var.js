function ChartModel2Var() {
    var totalWidth = 960,
        totalHeight = 500,
        mTop = 40,
        mBottom = 110,
        mRight = 70,
        mBottom = 110,
        mLeft = 70;

    var width, height, margin, margin2, height2;
    var timeScale = true;
    var yDomainSpec = null;
    var gEnter;
    var ticks = 4;
    var xValue = function(d) {
            return d[0];
        },
        yValue = function(d) {
            return d[1];
        };

    var xScale = d3.scaleTime(),
        x2Scale = d3.scaleTime(),
        yScale = d3.scaleLinear(),
        y2Scale = d3.scaleLinear();

    var xAxis = d3.axisBottom(),
        xAxis2 = d3.axisBottom(),
        yAxis = d3.axisLeft();

    var currentData;
    var currentSelection;

    var chart = function(selection) {
        currentSelection = selection;
        selection.each(function(data) {

            data = data.map(function(d, i) {
                return [xValue.call(data, d, i), yValue.call(data, d, i)];
            });

            currentData = data;

            margin = {
                top: mTop,
                right: mRight,
                bottom: mBottom,
                left: mLeft
            };
            margin2 = {
                top: 430 + (totalHeight - 500),
                right: mRight,
                bottom: 30,
                left: mLeft
            };
            width = totalWidth - margin.left - margin.right;
            height = totalHeight - margin.top - margin.bottom;
            height2 = totalHeight - margin2.top - margin2.bottom;

            xScale.domain(d3.extent(data, function(d) {
                    return d[0];
                }))
                .range([0, width]);

            yScale.domain(yDomainSpec ? yDomainSpec : [0, d3.max(data, function(d) {
                return d[1];
            })]);

            yScale.range([height, 0]);


            x2Scale.domain(d3.extent(data, function(d) {
                    return d[0];
                }))
                .range([0, width]);
            y2Scale.domain(yScale.domain())
                .range([height2, 0]);

            xAxis = (d3.axisBottom(xScale).ticks(ticks)),
                xAxis2 = d3.axisBottom(x2Scale),
                yAxis = d3.axisLeft(yScale);


            // Select the svg element, if it exists.
            var svg = d3.select(this).selectAll("svg").data([data]);

            // Otherwise, create the skeletal chart.
            gEnter = svg.enter().append("svg");

            gEnter.attr("width", totalWidth)
                .attr("height", totalHeight);

        });
    }

    chart.data = function() {
        return currentData;
    };

    chart.mTop = function(_) {
        if (!arguments.length) return mTop;
        mTop = +_;
        return chart;
    };

    chart.mBottom = function(_) {
        if (!arguments.length) return mBottom;
        mBottom = +_;
        return chart;
    };

    chart.mLeft = function(_) {
        if (!arguments.length) return mLeft;
        mLeft = +_;
        return chart;
    };

    chart.mRight = function(_) {
        if (!arguments.length) return mRight;
        mRight = +_;
        return chart;
    };

    chart.width = function(_) {
        if (!arguments.length) return totalWidth;
        totalWidth = +_;
        return chart;
    };

    chart.height = function(_) {
        if (!arguments.length) return totalHeight;
        totalHeight = +_;
        return chart;
    };

    chart.margin = function() {
        return margin;
    };

    chart.margin2 = function() {
        return margin2;
    };

    chart.marginedHeight = function() {
        return height;
    };

    chart.marginedWidth = function() {
        return width;
    };

    chart.marginedHeight2 = function() {
        return height2;
    };

    chart.x = function(_) {
        if (!arguments.length) return xValue;
        xValue = _;
        return chart;
    };

    chart.y = function(_) {
        if (!arguments.length) return yValue;
        yValue = _;
        return chart;
    };

    chart.timeScale = function(_) {
        if (!arguments.length) return timeScale;
        timeScale = _;
        xScale = timeScale ? d3.scaleTime() : d3.scaleLinear();
        x2Scale = timeScale ? d3.scaleTime() : d3.scaleLinear();
        return chart;
    };

    chart.ticks = function(_) {
        ticks = _;
        return chart;
    };

    chart.yDomainSpec = function(_) {
        yDomainSpec = _;
        return chart;
    };

    chart.yScale = function(_) {
       if (!arguments.length) return yScale;
            return yScale(_);
    };

    chart.xScale = function(_) {
    if (!arguments.length) return xScale;
        return xScale(_);
    };

    chart.x2Scale = function() {
        return x2Scale;
    };

    chart.y2Scale = function() {
        return y2Scale;
    };

    chart.selection = function() {
        return currentSelection;
    };

    chart.gEnter = function() {
        return gEnter;
    };

    chart.xAxis = function() {
        return xAxis;
    };

    chart.yAxis = function() {
        return yAxis;
    };

    chart.xAxis2 = function() {
        return xAxis2;
    };

    return chart;
}