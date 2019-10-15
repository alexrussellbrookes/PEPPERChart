function CustomChart() {
    let totalWidth = 960,
        totalHeight = 500,
        mTop = 40,
        mBottom = 110,
        mRight = 70,
        mLeft = 70;

    let entered = false;    
    let width, height, margin;
    let timeScale = true;
    let yDomainSpec = null;
    let xDomainSpec = null;
    let xScale = d3.scaleTime(),
        yScale = d3.scaleLinear();
    let xAxis = d3.axisBottom(),
        yAxis = d3.axisLeft();

    let currentSelection;
    let gEnter;
    let ticks = 4;

    let createSet = [];
    let updateSet = [];

    function chart(selection) {
        currentSelection = selection;
        selection.each(function() {

            margin = {
                top: mTop,
                right: mRight,
                bottom: mBottom,
                left: mLeft
            };
            width = totalWidth - margin.left - margin.right;
            height = totalHeight - margin.top - margin.bottom;

            xScale.range([0, width]);
            if (xDomainSpec) {
                xScale.domain(xDomainSpec);
            }

            yScale.range([height, 0]);
            if (yDomainSpec) {
                yScale.domain(yDomainSpec);
            }

            xAxis = (d3.axisBottom(xScale).ticks(ticks)),
            yAxis = d3.axisLeft(yScale);

            if(!entered) {
                d3.select(this).append("svg");
                entered = true;
            }

            gEnter = d3.select(this).selectAll("svg");

            gEnter.attr("width", totalWidth)
                .attr("height", totalHeight);

            gEnter = gEnter.append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

            for (var i = 0; i < createSet.length; i += 1) {
                createSet[i].create(xScale, yScale, gEnter);
            }

        });
    }

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

    chart.timeScale = function(_) {
        if (!arguments.length) return timeScale;
        timeScale = _;
        xScale = timeScale ? d3.scaleTime() : d3.scaleLinear();
        return chart;
    };

    chart.ticks = function(_) {
        ticks = _;
        return chart;
    };

    chart.xDomainSpec = function(_) {
        xDomainSpec = _;
        return chart;
    }

    chart.yDomainSpec = function(_) {
        yDomainSpec = _;
        return chart;
    };

    chart.yScale = function(input) {
        return yScale(input);
    };

    chart.xScale = function(input) {
        return xScale(input);
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

    chart.addCreateItem = function(_) {
        createSet.push(_);
    };

    chart.addUpdateItem = function(_) {
        updateSet.push(_);
    };

    return chart;
}