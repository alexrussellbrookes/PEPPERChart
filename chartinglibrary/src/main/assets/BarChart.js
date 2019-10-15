function BarChart(chartModel) {
    let yAxisLabel = null;
    let xAxisLabel = null;
    let chartID = "bar";
    let fontSize = "18";
    let barLabels = true;
    let margin = chartModel.margin();
    let width = chartModel.marginedWidth();
    let height = chartModel.marginedHeight();

    let xKey = chartModel.xKey();
    let xValue = chartModel.x();
    let yValue = chartModel.y();
    let xScale = chartModel.xScale();
    let yScale = chartModel.yScale();
    let xAxis = chartModel.xAxis();
    let yAxis = chartModel.yAxis();
    let yVarArray = chartModel.yVarArray();
    let colors = d3.scaleOrdinal(d3.schemeCategory10);
    let z = d => colors(d.key);
    let legend = false;

    let data, focus, bar, series, axisX, axisY, gEnter;
    let selection = chartModel.selection();
    const createSet = [];
    const updateSet = [];

    function chart() {
        data = chartModel.data();
        createSeries(data);

        xScale.domain(data.map(d => xValue(d)))
                .rangeRound([0, width])
                .padding(0.1);

        yScale.domain([d3.min(series, stackMin), d3.max(series, stackMax)])
                .rangeRound([height, 0]);

        if (data.length > 10) {
            z = d => getColour(d);
        }

        selection.each(function (data) {

            gEnter = chartModel.gEnter();
            if (legend) {
                makeLegend();
            }
            focus = PathFunctions.makeFocus(gEnter, margin, width, height);
            new XAxis(focus, "focusXAxis", height, width, xAxis, fontSize, xAxisLabel);
            new YAxis(focus, yAxis, fontSize, yAxisLabel);

            bar = focus.selectAll(".bar")
                    .data(series)
                    .enter().append("g")
                    .attr("class", "bar")
                    .attr("fill", d => z(d))
                    .attr("id", d => d.key);
            getBarSelection(bar);

            bar.update = function (data) {
                this.selectAll("rect")
                        .data(d => d)
                        .transition()
                        .attr("height", d => getBarHeight(d))
                        .attr("y", d => getBarY(d));
                getBarSelection(this)
                        .exit().remove();

                this.selectAll("text")
                        .data(d => d)
                        .transition()
                        .attr("y", d => getTextY(d));
                getTextSelection(this)
                        .exit().remove();
            };
            updateSet.push(bar);

            getTextSelection(bar);
            if (barLabels) {
                makeBarTextSelection(focus);
            }

            for (var i = 0; i < createSet.length; i += 1) {
                createSet[i].create(xScale, yScale, focus);
                updateSet.push(createSet[i]);
            }
        });
    }

    function getColour(d) {
        return d3.interpolateSpectral((d.index + 1) / d.length);
    }

    function createSeries(data) {
        series = d3.stack()
                .offset(d3.stackOffsetDiverging)
                .keys(yVarArray)
                (data);
    }

    function makeLegend() {
        margin.bottom += 50;
        const legend = gEnter.selectAll(".legend")
                .data(series)
                .enter().append("g")
                .attr("class", "legend")
                .attr("transform", (d, i) => `translate(${i * xScale.bandwidth() + 20},${height + margin.top + 40})`);

        legend.append("rect")
                .attr("width", xScale.bandwidth)
                .attr("height", 25)
                .attr("fill", d => z(d));

        legend.append("text")
                .attr("y", 32)
                .attr("dy", "0.75em")
                .attr("font-size", fontSize)
                .attr("font-family", "sans-serif")
                .text(d => d.key);
    }

    function makeBarTextSelection(selection) {
        let bars = selection.selectAll(".bar");
        bars.selectAll(function () {
            let bartext = d3.select(this).attr("id");
            d3.select(this).selectAll("text").text(bartext);
        });
    }

    function getBarHeight(d) {
        return yScale(d[0]) - yScale(d[1]);
    }

    function getBarY(d) {
        return yScale(d[1]);
    }

    function getTextY(d) {
        return yScale(d[0]) - yScale(d[1]) === 0 ? null : yScale(d[1]);
    }

    function getTextSelection(selection) {
        return selection.selectAll("text")
                .data(d => d)
                .enter()
                .append("text")
                .attr("x", d => xScale(d.data[xKey]))
                .attr("y", d => getTextY(d))
                .attr("dy", "2em")
                .attr("dx", "1em")
                .attr("font-size", "14")
                .attr("font-family", "sans-serif")
                .attr("fill", "white");
    }

    function getBarSelection(selection) {
        return selection.selectAll("rect")
                .data(d => d)
                .enter().append("rect")
                .attr("id", d => d.data[xKey])
                .attr("width", xScale.bandwidth)
                .attr("x", d => xScale(d.data[xKey]))
                .attr("y", d => getBarY(d))
                .attr("height", d => getBarHeight(d));
    }

    function stackMin(serie) {
        return d3.min(serie, d => d[0]);
    }

    function stackMax(serie) {
        return d3.max(serie, d => d[1]);
    }

    function update() {
        data = chartModel.data();
        createSeries(data);
        bar.data(series);
        for (let i = 0; i < updateSet.length; i += 1) {
            updateSet[i].update ? updateSet[i].update(data) : updateSet[i].refresh(data);
        }
    }

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

    chart.fontSize = function (_) {
        fontSize = _;
    };

    chart.barLabels = function (_) {
        barLabels = _;
    };

    chart.legend = function (_) {
        legend = _;
    };

    chart.yAxisLabel = function (_) {
        yAxisLabel = _;
    };

    chart.xAxisLabel = function (_) {
        xAxisLabel = _;
    };

    chart.chartID = function (_) {
        chartID = _;
    };

    chart.addUpdateItem = function (_) {
        updateSet.push(_);
    };

    chart.addCreateItem = function (_) {
        createSet.push(_);
    };

    chart.update = function () {
        return update();
    };

    chart.draw = function () {
        return chart();
    };

    return chart;
}