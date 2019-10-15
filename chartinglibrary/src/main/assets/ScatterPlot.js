/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function ScatterPlot(chartModel) {
    let graph = UpdateableTupleChart.call(this, chartModel);
    let generator = d3.line()
            .defined(d => d[1] === null ? null : d)
            .x(graph.X())
            .y(graph.Y());
    let generator2 = d3.line()
            .defined(d => d[1] === null ? null : d)
            .x(graph.X2())
            .y(graph.Y2());
        
    graph.contextType("dot");
    graph.setGenerator(generator);
    graph.setGenerator2(generator2);
    graph.setGeneratorType("line");
    graph.setDotGeneratorX(generator.x());
    graph.setDotGeneratorY(generator.y());
    
    return graph;
}

