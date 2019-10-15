function AreaChart(chartModel) {
    let graph = LineGraph.call(this, chartModel);

    let area = d3.area()
            .defined(d => d[1] == null ? null : d)
            .curve(graph.curveType())
            .x(graph.X())
            .y0(graph.height())
            .y1(graph.Y());

    let area2 = d3.area()
            .defined(d => d[1] == null ? null : d)
            .curve(graph.curveType())
            .x(graph.X2())
            .y0(graph.height2())
            .y1(graph.Y2());
    
        
    graph.contextType("area");    
    graph.setGenerator(area);
    graph.setGenerator2(area2);
    graph.setGeneratorType("area");
    graph.setDotGeneratorX(area.x());
    graph.setDotGeneratorY(area.y1());
    graph.addComponent("area");
    
    return graph;
}