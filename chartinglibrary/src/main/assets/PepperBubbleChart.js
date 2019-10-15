function PepperBubbleChart() {
    let graph = BubbleChart.call(this);
    let rectDisplay = false;
    let totalTextValue = "Total";
    let unitTextValue = "units";
    let sumTextValue = null;
    
    graph.rectDisplay = function(_) {
        if (_) {
            graph.componentType("rect");
        }
    };
    graph.totalText = function (_) {
        totalTextValue = _;
    };

    graph.unitText = function (_) {
        unitTextValue = _;
    };

    graph.sumText = function (_) {
        sumTextValue = _;
    };

    graph.sideBar = function (_) {
        if (_) {
            let sideBar = {};
            sideBar.create = function (xScale, yScale, g) {
                this.g = g;
                let textGroup = g.append("g")
                        .attr("transform", `translate(${graph.width() + 20}, 10)`);
                let textBlock = textGroup.append("text")
                        .attr("x", "0")
                        .attr("y", "0")
                        .attr("id", "textBlock")
                        .attr("fill", "grey")
                        .attr("font-size", graph.fontSize());
                textBlock.append("tspan")
                        .attr("x", "0")
                        .html(totalTextValue);
                textBlock.append("tspan")
                        .attr("id", "Total")
                        .attr("x", "0")
                        .attr("font-weight", "bold")
                        .attr("dy", "1.2em")
                        .html(sumTextValue ? sumTextValue : graph.totalCount());
                textBlock.append("tspan")
                        .attr("x", "0")
                        .attr("dy", "1.2em")
                        .html(unitTextValue);
            };
            sideBar.update = function (data) {
                if (!sumTextValue) {
                    let carbsTextTotal = this.g.select("#Total");
                    carbsTextTotal.html(graph.totalCount());
                }
            }
            graph.addCreateItem(sideBar);
        }
    };
    
    return graph;
}

