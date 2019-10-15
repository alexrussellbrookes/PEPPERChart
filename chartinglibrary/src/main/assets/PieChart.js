function PieChart(chartModel) {

    let chartID = "pie";
    let fontSize = "18";
    let label = false;
    let legend = true;
    let width = chartModel.marginedWidth();
    let height = chartModel.marginedHeight();
    let data;
    let offSet = 150;
    let selection = chartModel.selection();
    let path;
    let arcs;
    let arc;
    let pie;
    let labelArc;
    let centre;
    let translate;
    let gEnter;
    var chart = function () {

        data = chartModel.data();
        let palette = d3.scaleOrdinal(d3.schemeSet3);
        colour = (d, data) => palette(d.index);
        if (data.length > 12) {
            colour = getSpectralColour;
        }

        let radius = Math.min(width, height) / 1.5;
        pie = d3.pie()
                .value(d => +d[1])
                .sort(null);
        arcs = pie(data);
        arc = d3.arc()
                .innerRadius(0)
                .outerRadius(radius - 10);
        labelArc = d3.arc()
                .innerRadius(radius - offSet)
                .outerRadius(radius - 10);
        selection.each(function (data) {
            gEnter = chartModel.gEnter();
            translate = gEnter.append("g")
                    .attr("transform", `translate(${width / 2}, ${height / 1.5})`);
            path = translate.selectAll("path")
                    .data(pie)
                    .enter().append("path")
                    .attr("fill", d => colour(d, data))
                    .attr("stroke", "black")
                    .attr("stroke-width", "1")
                    .attr("d", arc);
            centre = translate.append("g");
            if (label) {
                arcs.forEach(function (d) {
                    let c = labelArc.centroid(d);
                    centre.append("text")
                            .attr("fill", "black")
                            .attr("x", c[0])
                            .attr("y", c[1])
                            .text(d.data[0]);

                });
            }
        });
        if (legend) {
            makeLegend();
        }
    };
    getSpectralColour = (d, data) => d3.interpolateSpectral((d.index + 1) / data.length);
    function update() {
        data = chartModel.data();
        arcs = pie(data);
        let paths = gEnter.selectAll("path")
                .data(pie)
                .transition(1000)
                .attr("d", arc);
        translate.selectAll("g")
                .remove();
        centre = translate.append("g");
        arcs.forEach(function (d) {
            let c = labelArc.centroid(d);
            centre.append("text")
                    .attr("fill", "black")
                    .attr("x", c[0])
                    .attr("y", c[1])
                    .text(d.data[0]);
        });
    }

    function makeLegend() {
        const rectHeight = 50;
        const legend = gEnter.selectAll(".legend")
                .data(data)
                .enter().append("g")
                .attr("class", "legend")
                .attr("transform", (d, i) => `translate(800,${i * rectHeight  + 100})`);
        legend.append("rect")
                .attr("width", 150)
                .attr("height", rectHeight)
                .attr("fill", (d, i) => colour({index: i}, data));
        legend.append("text")
                .attr("y", 25)
                .attr("font-size", fontSize)
                
                .text(d => d[0]);
    }

    chart.fontSize = function (_) {
        fontSize = _;
    };
    chart.offSet = function (_) {
        offSet = _;
    };
    chart.chartID = function (_) {
        chartID = _;
    };
    chart.legend = function (_) {
        legend = _;
    };
    chart.label = function(_) {
        label = _;
    };
    chart.update = function () {
        return update();
    };
    chart.draw = function () {
        return chart();
    };
    return chart;
}