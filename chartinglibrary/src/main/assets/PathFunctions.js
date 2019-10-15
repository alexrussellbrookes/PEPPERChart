/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class PathFunctions {
    static makeTargetLine(selection, targetLines, xScale, yScale, data) {
        for (var i = 0; i < targetLines.length; i++) {
            let value = targetLines[i];
            let targetLine = selection.append("g")
                    .attr("class", "targetLine");
            targetLine.append("line")
                    .attr("x1", d3.min(data, function (d) {
                        return xScale(d[0]);
                    }))
                    .attr("y1", yScale(value))
                    .attr("x2", d3.max(data, function (d) {
                        return xScale(d[0]);
                    }))
                    .attr("y2", yScale(value))
                    .style('shape-rendering', 'crispEdges')
                    .style("stroke", "grey");
        }
    }

    static makeClipPath(selection, chartID, width, height) {
        selection.append("defs").append("clipPath")
                .attr("id", chartID + "Clip")
                .append("rect")
                .attr("width", width)
                .attr("height", height)
                .attr("x", 0)
                .attr("y", 0);
    }

    static makeToolTips(selection, chartID) {
        let tooltip = selection.append("div")
                .attr("class", chartID + "tooltip")
                .style("opacity", "0");
        return tooltip;

    }

    static makeFocus(gEnter, margin, width, height) {
        let focus = gEnter.append("g")
                .attr("class", "focus")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        focus.append("rect")
                .attr("width", width)
                .attr("height", height)
                .attr("fill", "#FFF");

        return focus;
    }

    static makeContext(context, data, margin2, height, height2, strokeColor, chartID, line2, xScale2, xAxis2, fontSize, newDomain,
            updateSet, timeScale, contextTimeFormat, brush, focus, zoom, xAxis, yAxis, contextType, oldX2Scale, updateMode, 
            UPDATE_PATTERN) {

        new Context(context, data, margin2, height, height2, strokeColor, chartID, line2, xScale2, xAxis2,
                fontSize, newDomain, contextType, updateSet, oldX2Scale, updateMode, UPDATE_PATTERN);

        if (timeScale) {
            context.call((xAxis2.tickFormat(d3.timeFormat(contextTimeFormat))));
        }
        context.append("g")
                .attr("class", "brush")
                .call(brush)
                .call(brush.move, xScale2.range());

        focus.call(zoom);

        focus.select(".axis--x")
                .call(xAxis);

        focus.select(".axis--y").call(yAxis);

        context.select(".axis--x").call(xAxis2);

        context.select(".brush")
                .call(brush)
                .call(brush.move, xScale2.range());

        focus.call(zoom.transform, d3.zoomIdentity);
    }
    
    static createZoom(zoom, zoomed, brush, brushed, width, height, height2) {
        zoom.scaleExtent([1, Infinity])
                .translateExtent([[0, 0], [width, height]])
                .extent([[0, 0], [width, height]])
                .on("zoom", zoomed);

        brush.extent([[0, 0], [width, height2]])
                .on("brush end", brushed);
    }

    static pathUpdate(click, tooltip, newDomain, xScale, data, chartModel, zoomOn, context, focus, xAxis2, brush, zoom, oldXScale,
            updateSet, x2Scale, xAxis, yAxis, currentDataSet) {
        if (click.isClicked) {
            tooltip.style("opacity", 0);
            click.isClicked = false;
        }

        newDomain.domain = xScale.domain();
        data = chartModel.data();
        
        if (zoomOn) {
            context.select(".axis--x")
                    .transition()
                    .duration(1000)
                    .call(xAxis2);

            context.select(".brush")
                    .transition()
                    .call(brush.move, xScale.range());

            focus.transition()
                    .call(zoom.transform, d3.zoomIdentity);
        }
        
        for (let i = 0; i < updateSet.length; i += 1) {
            updateSet[i].update ? updateSet[i].update(data) : updateSet[i].refresh(data);
        }
        
        xScale.domain(newDomain.domain);
        x2Scale.domain(newDomain.domain);

        focus.select(".axis--x")
                .transition()
                .duration(1000)
                .call(xAxis);

        focus.select(".axis--y")
                .call(yAxis);

        currentDataSet.data = data;
        oldXScale.domain = newDomain.domain;
    }

}
