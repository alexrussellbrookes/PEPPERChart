function LineGraph(chartModel) {
    let graph = UpdateableTupleChart.call(this, chartModel);
    
    generator = d3.line()
            .defined(d => d[1] === null ? null : d)
            .curve(graph.curveType())
            .x(graph.X())
            .y(graph.Y());
    generator2 = d3.line()
            .defined(d => d[1] === null ? null : d)
            .curve(graph.curveType())
            .x(graph.X2())
            .y(graph.Y2());
    
    graph.contextType("line");
    graph.setGenerator(generator);
    graph.setGenerator2(generator2);
    graph.setGeneratorType("line");
    graph.setDotGeneratorX(generator.x());
    graph.setDotGeneratorY(generator.y());
    graph.addComponent("line");
    
    return graph;
}
