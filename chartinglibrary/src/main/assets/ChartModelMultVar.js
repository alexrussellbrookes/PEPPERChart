function ChartModelMultVar(xKey, yVar) {
    var totalWidth = 960,
        totalHeight = 500,
        mTop = 40,
        mBottom = 110,
        mRight = 70,
        mBottom = 110,
        mLeft = 70;

    var width, height, margin, margin2, height2;
    var timeScale = true;
    var ordinalScale=false;
    var yDomainSpec = null;
    var gEnter;
    var ticks = 4;
    var originalData;
    var xValue = function(d) {
            return d[0];
        },
        yValue = function(d) {
            return d[1];
        };
    var xScale = d3.scaleTime(),
        yScale = d3.scaleLinear();
    var xAxis = d3.axisBottom(),
        yAxis = d3.axisLeft();
    var currentSelection;
    var yVarArray = yVar;
    var xKey = xKey;

    var chart = function(selection) {

        currentSelection = selection;

        selection.each(function(data) {

          	originalData = data;

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

            xScale.range([0, width]);

            yScale.range([height, 0]);

            xAxis = (d3.axisBottom(xScale).ticks(ticks)),
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
      return originalData;
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
        if (timeScale) ordinalScale = false;
        xScale = timeScale ? d3.scaleTime() : d3.scaleLinear();
        return chart;
    };

    chart.ordinalScale = function(_) {
        if (!arguments.length) return ordinalScale;
        ordinalScale = _;
        if (ordinalScale) timeScale = false;
        xScale = ordinalScale ? d3.scaleBand() : d3.scaleLinear();
        return chart;
    };

    chart.ticks = function(_) {
        ticks = _;
        return chart;
    };

    chart.yDomainSpec = function(_) {
        if (!arguments.length) return yDomainSpec;
        yDomainSpec = _;
        return chart;
    };

    chart.yVarArray = function() {
        return yVarArray;
    };

    chart.xKey = function() {
        return xKey;
    };

    chart.yScale = function() {
        return yScale;
    };

    chart.xScale = function() {
        return xScale;
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

    return chart;
}