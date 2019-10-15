function UpdateableTupleChart(chartModel) {
    let targetLines = [];
    let dataPoints = false;
    let zoomOn = false;
    let yAxisLabel = null;
    let xAxisLabel = null;
    let isClicked = false;
    let currentDataSet = {};
    let strokeColor = "#FF00FF";
    let curveType = d3.curveLinear;
    let fontSize = "18";

    let tooltip;
    let tooltipLabelX = null;
    let tooltipLabelY = null;
    let contextTimeFormat = "%a %I %p";
    let tooltipTimeFormat = "%a %H:%M";
    let toolTipFormat;

    let chartID = "line";
    const UPDATE_PATTERN = {
        slide: 1,
        fade: 2,
        generalUpdate: 3
    };

    const zoom = d3.zoom();
    const brush = d3.brushX();
    const bisectDate = d3.bisector(function (d) {
        return d[0];
    }).left;

    let xScale, x2Scale, yScale, y2Scale, oldXScale, oldX2Scale, newDomain;

    const timeScale = chartModel.timeScale();
    const margin = chartModel.margin();
    const margin2 = chartModel.margin2();
    const width = chartModel.marginedWidth();
    const height = chartModel.marginedHeight();
    const height2 = chartModel.marginedHeight2();
    const xValue = chartModel.x();
    const yValue = chartModel.y();
    const xAxis = chartModel.xAxis();
    const xAxis2 = chartModel.xAxis2();
    const yAxis = chartModel.yAxis();

    let data = chartModel.data();
    let oldData;

    let selection = chartModel.selection();
    let focus, context, gEnter;

    let createSet = [];
    let refreshSet = [];
    let updateSet = [];
    let updateMode = {type: UPDATE_PATTERN.generalUpdate};
    let generatorType, generator, generator2;
    let addComponent = null;
    let dotGeneratorX, dotGeneratorY, contextType;
    let click = {isClicked: false};


    function chart() {
        selection.each(function (data) {
            data = data.map(function (d, i) {
                return [xValue.call(data, d, i), yValue.call(data, d, i)];
            });
                        
            currentDataSet.data = data;

            xScale = chartModel.xScale();
            oldXScale = {domain: xScale.domain()};
            newDomain = {domain: xScale.domain()};
            x2Scale = chartModel.x2Scale();
            oldX2Scale = {domain: x2Scale.domain()};
            yScale = chartModel.yScale();
            y2Scale = chartModel.y2Scale();

            toolTipFormat = d3.timeFormat(tooltipTimeFormat);

            gEnter = chartModel.gEnter();

            PathFunctions.createZoom(zoom, zoomed, brush, brushed, width, height, height2);
            PathFunctions.makeClipPath(gEnter, chartID, width, height);
            tooltip = PathFunctions.makeToolTips(d3.select("body"), chartID);

            focus = PathFunctions.makeFocus(gEnter, margin, width, height);
            PathFunctions.makeTargetLine(focus, targetLines, xScale, yScale, data);

            if (addComponent) {
                let component = new PathUpdate(focus, data, strokeColor, chartID, generatorType, generator, updateMode, 
                    UPDATE_PATTERN, xScale, oldXScale, newDomain);
                updateSet.push(component);
                refreshSet.push(component);
            }

            let axisX = new XAxis(focus, "focusXAxis", height, width, xAxis, fontSize, xAxisLabel);
            refreshSet.push(axisX);
            new YAxis(focus, yAxis, fontSize, yAxisLabel);

            if (dataPoints) {
                let dots = new VisibleDots("dot", false, focus, chartID, data, updateMode, UPDATE_PATTERN, xScale, dotGeneratorX,
                        dotGeneratorY, oldXScale, newDomain, strokeColor, touchstart);
                updateSet.push(dots);
                if (zoomOn) {
                    refreshSet.push(dots);
                }
                let invisibles = new InvisibleDots("invisible", true, focus, chartID, data, updateMode, UPDATE_PATTERN, xScale,
                        dotGeneratorX, dotGeneratorY, oldXScale, newDomain, strokeColor, touchstart);
                updateSet.push(invisibles);
                refreshSet.push(invisibles);
            }

            ChartArrayUtils.makeCreateSet(createSet, refreshSet, updateSet, xScale, yScale, focus);

            if (zoomOn) {
                context = gEnter.append("g");
                PathFunctions.makeContext(context, data, margin2, height, height2, strokeColor, chartID, generator2, x2Scale,
                        xAxis2, fontSize, newDomain, updateSet, timeScale, contextTimeFormat, brush, focus, zoom, xAxis, yAxis,
                        contextType, oldX2Scale, updateMode, UPDATE_PATTERN);
            }
        });
    }
    
    function zoomed() {
        if (d3.event.sourceEvent && d3.event.sourceEvent.type === "brush")
            return; // ignore zoom-by-brush
        if (click.isClicked) {
            tooltip.style("opacity", 0);
            click.isClicked = false;
        }
        var t = d3.event.transform;
        xScale.domain(t.rescaleX(x2Scale).domain());
        for (var i = 0; i < refreshSet.length; i += 1) {
            refreshSet[i].refresh(data);
        }
        context.select(".brush").call(brush.move, xScale.range().map(t.invertX, t));
    }
    
    function brushed() {
        if (d3.event.sourceEvent && d3.event.sourceEvent.type === "zoom")
            return; // ignore brush-by-zoom
        if (click.isClicked) {
            tooltip.style("opacity", 0);
            click.isClicked = false;
        }
        var s = d3.event.selection || x2Scale.range();
        xScale.domain(s.map(x2Scale.invert, x2Scale));
        for (var i = 0; i < refreshSet.length; i += 1) {
            refreshSet[i].refresh(data);
        }
        focus.call(zoom.transform, d3.zoomIdentity
                .scale(width / (s[1] - s[0]))
                .translate(-s[0], 0));
    }
    
    function touchstart() {
        tooltip = d3.select("." + chartID + "tooltip");
        if (!click.isClicked) {
            let x0 = xScale.invert(d3.mouse(this)[0]);
            let i = bisectDate(currentDataSet.data, x0, 1),
                    d0 = currentDataSet.data[i - 1],
                    d1 = currentDataSet.data[i],
                    d = x0 - d0[0] > d1[0] - x0 ? d1 : d0;
            
            let rectobj = d3.event.srcElement.getBoundingClientRect();
            let xPos = (X(d) + margin.left - rectobj.width);
            tooltip.transition()
                    .duration(200)
                    .style("opacity", 0.9);

            let tooltipTextX = timeScale ? toolTipFormat(d[0]) : tooltipLabelX ? tooltipLabelX + ": " + d[0] : d[0];
            let tooltipTextY = tooltipLabelY ? tooltipLabelY + ": " + d[1] : d[1];
            tooltip.html(`${tooltipTextX}<br/>${tooltipTextY}`);
            xPos < 760 ? tooltip.style("left", `${X(d) + margin.left - rectobj.width}px`) : tooltip.style("left", 760);
            tooltip.style("top", `${rectobj.top + window.scrollY}px`);
            click.isClicked = true;

        } else {
            tooltip.transition()
                    .duration(500)
                    .style("opacity", 0);
            click.isClicked = false;
        }
    }

    function X(d) {
        return xScale(d[0]);
    }

    function X2(d) {
        return x2Scale(d[0]);
    }

    // The x-accessor for the path generator; yScale ∘ yValue.
    function Y(d) {
        return yScale(d[1]);
    }

    function Y2(d) {
        return y2Scale(d[1]);
    }

    chart.X = function () {
        return X;
    };

    chart.Y = function () {
        return Y;
    };

    chart.X2 = function () {
        return X2;
    };

    chart.Y2 = function () {
        return Y2;
    };

    chart.height = function () {
        return height;
    };

    chart.height2 = function () {
        return height2;
    };

    chart.width = function () {
        return width;
    };

    chart.x = function (_) {
        if (!arguments.length)
            return xValue;
        xValue = _;
    };

    chart.y = function (_) {
        if (!arguments.length)
            return yValue;
        yValue = _;
    };

    chart.targetLine = function (_) {
        if (typeof _ === 'number')
            targetLines.push(_);
    };

    chart.dataPoints = function (_) {
        dataPoints = _;
    };

    chart.zoomOn = function (_) {
        zoomOn = _;
    };

    chart.strokeColor = function (_) {
        strokeColor = _;
    };

    chart.fontSize = function (_) {
        fontSize = _;
    };

    chart.addUpdateItem = function (_) {
        updateSet.push(_);
    };

    chart.addCreateItem = function (_) {
        createSet.push(_);
    };

    chart.addRefreshItem = function (_) {
        refreshSet.push(_);
    };

    chart.curveType = function (_) {
        if (!arguments.length)
            return curveType;
        curveType = _;
    };

    chart.yAxisLabel = function (_) {
        yAxisLabel = _;
    };

    chart.xAxisLabel = function (_) {
        xAxisLabel = _;
    };

    chart.tooltipLabelX = function (_) {
        tooltipLabelX = _;
    };

    chart.tooltipLabelY = function (_) {
        tooltipLabelY = _;
    };

    chart.chartID = function (_) {
        chartID = _;
    };

    chart.gEnter = function () {
        return gEnter;
    };

    chart.yScale = function (input) {
        if (!arguments.length)
            return yScale;
        return yScale(input);
    };

    chart.xScale = function (input) {
        if (!arguments.length)
            return xScale;
        return xScale(input);
    };

    chart.contextTimeFormat = function (_) {
        if (!arguments.length)
            return contextTimeFormat;
        contextTimeFormat = _;
    };

    chart.tooltipTimeFormat = function (_) {
        if (!arguments.length)
            return tooltipTimeFormat;
        tooltipTimeFormat = _;
    };

    chart.updateMode = function (_) {
        updateMode.type = _;
    };

    chart.setGenerator = function (_) {
        generator = _;
    };

    chart.setGenerator2 = function (_) {
        generator2 = _;
    };

    chart.setGeneratorType = function (_) {
        generatorType = _;
    };

    chart.setDotGeneratorX = function (_) {
        dotGeneratorX = _;
    };

    chart.setDotGeneratorY = function (_) {
        dotGeneratorY = _;
    };

    chart.contextType = function (_) {
        contextType = _;
    };

    chart.addComponent = function (_) {
        addComponent = _;
    };

    chart.draw = function () {
        chart();
    };

    chart.update = function () {
        return PathFunctions.pathUpdate(click, tooltip, newDomain, xScale, data, chartModel, zoomOn, context, focus, xAxis2, brush,
                zoom, oldXScale, updateSet, x2Scale, xAxis, yAxis, currentDataSet);
    };

    return chart;
}



