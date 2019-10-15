/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Path {
    constructor(selection, data, strokeColor, chartID, pathType, generator) {
        this.selection = selection;
        this.strokeColor = strokeColor;
        this.chartID = chartID;
        this.generator = generator;

        this.pathSelection = this.selection.append("path")
                .datum(data)
                .attr("fill", pathType === "line" ? "none" : this.strokeColor)
                .attr("stroke", this.strokeColor)
                .attr("stroke-width", 1.5)
                .attr("class", this.chartID + pathType)
                .attr("d", this.generator);
    }

    getSelection() {
        return this.pathSelection;
    }

    refresh(data) {
        this.pathSelection
                .attr("d", this.generator);
    }
}

class PathUpdate extends Path {
    constructor(selection, data, strokeColor, chartID, pathType, generator, updateMode, UPDATE_PATTERN, xScale, oldXScale, newDomain) {
        super(selection, data, strokeColor, chartID, pathType, generator);
        this.updateMode = updateMode;
        this.UPDATE_PATTERN = UPDATE_PATTERN;
        this.oldXScale = oldXScale;
        this.newDomain = newDomain;
        this.xScale = xScale;
    }

    update(data) {
        if (this.updateMode.type === this.UPDATE_PATTERN.slide) {
            this.xScale.domain(this.oldXScale.domain);
            this.pathSelection.datum(data)
                    .attr("stroke-width", 1.5)
                    .attr("d", this.generator);
            this.xScale.domain(this.newDomain.domain);
            this.pathSelection.transition()
                    .duration(1000)
                    .attr("d", this.generator);
        }
        if (this.updateMode.type === this.UPDATE_PATTERN.fade) {
            this.xScale.domain(this.newDomain.domain);
            this.pathSelection.datum(data)
                    .attr("stroke-width", 0)
                    .attr("d", this.generator);
            this.pathSelection.transition()
                    .duration(1000)
                    .attr("stroke-width", 1.5);
        }

        if (this.updateMode.type === this.UPDATE_PATTERN.generalUpdate) {
            this.xScale.domain(this.newDomain.domain);
            this.pathSelection.datum(data)
                    .attr("stroke-width", 1.5)
                    .attr("d", this.generator);
        }
    }
}

