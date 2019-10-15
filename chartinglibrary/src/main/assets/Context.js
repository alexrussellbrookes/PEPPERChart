/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class Context {
    constructor(selection, data, margin2, height, height2, strokeColor, chartID, accessor, x2Scale, xAxis2, fontSize,
            newDomain, contextType, updateSet, oldXScale2, updateMode, UPDATE_PATTERN) {
        this.x2Scale = x2Scale;
        this.newDomain = newDomain;
        this.contextType = contextType;
        this.accessor = accessor;
        
        this.context = selection.attr("class", "context")
                .attr("transform", `translate(${margin2.left}, ${margin2.top})`);      
        this.contextPath = null;
        if (contextType === "line") {
            this.contextPath = new Path(this.context, data, strokeColor, chartID, "line", accessor);
        } else if (contextType === "area") {
            this.contextPath = new Path(this.context, data, strokeColor, chartID, "area", accessor);
        } else if (contextType === "dot") {
            this.contextPath = new VisibleDots("dot", false, this.context, chartID, data, updateMode, UPDATE_PATTERN, x2Scale,
            accessor.x(), accessor.y(), oldXScale2, newDomain, strokeColor);
        }

        if (this.contextPath) {
            updateSet.push(this);
        }
        this.axis = new XAxis(this.context, "contextAxis", height2, 0, xAxis2, fontSize, false, (xAxis2).ticks(3));
    }

    update(data) {
        this.x2Scale.domain(this.newDomain.domain);
        if (this.contextType === "line" || this.contextType === "area") {
            this.contextPath.pathSelection.datum(data)
                    .attr("d", this.accessor);
        }
    }
}


