/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Dots {
    constructor(dotType, clickable, focus, chartID, data, xCoord, yCoord, strokeColor, touchstart) {
        this.dotType = dotType;
        this.chartID = chartID;
        this.focus = focus;
        this.data = data;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.strokeColor = strokeColor;
        this.touchstart = touchstart;
        this.clickable = clickable;
        this.selection;

        let dots = this.focus.selectAll("." + chartID + dotType)
                .data(data.filter(d => d[1] === null ? null : d))
                .enter();
        this.addDots(dots, dotType, clickable);
    }

    refresh(data) {
        this.focus.selectAll("." + this.chartID + this.dotType)
                .attr("cx", this.xCoord)
                .attr("cy", this.yCoord);
    }

    addDots(selection, label, clickable) {
        let newSelec = selection.append("circle");
        newSelec.attr("class", this.chartID + label)
                .attr("cx", this.xCoord)
                .attr("cy", this.yCoord)
                .attr("r", 3.5)
                .attr("stroke", this.strokeColor);
        if (clickable)
            newSelec.on("touchstart", this.touchstart);
    }
}

class UpdateableDots extends Dots {
    constructor(dotType, clickable, focus, chartID, data, updateMode, UPDATE_PATTERN, xScale, xCoord, yCoord, oldXScale, newDomain,
            strokeColor, touchstart) {
        super(dotType, clickable, focus, chartID, data, xCoord, yCoord, strokeColor, touchstart);
        this.xScale = xScale;
        this.updateMode = updateMode;
        this.UPDATE_PATTERN = UPDATE_PATTERN;
        this.oldXScale = oldXScale;
        this.newDomain = newDomain;
    }
}

class VisibleDots extends UpdateableDots {

    constructor(dotType, clickable, focus, chartID, data, updateMode, UPDATE_PATTERN, xScale, xCoord, yCoord, oldXScale, newDomain,
            strokeColor, touchstart) {
        super(dotType, clickable, focus, chartID, data, updateMode, UPDATE_PATTERN, xScale, xCoord, yCoord, oldXScale, newDomain,
                strokeColor, touchstart);
    }

    update(data) {
        let newDots = this.focus.selectAll("." + this.chartID + this.dotType);

        if (this.updateMode.type === this.UPDATE_PATTERN.slide) {
            this.xScale.domain(this.oldXScale.domain);
            let entry = newDots.data(data.filter(d => d[1] == null ? null : d))
                    .enter();
            this.addDots(entry, this.dotType, false);
            newDots.exit().remove();
            newDots = this.focus.selectAll("." + this.chartID + this.dotType)
                    .attr("r", 3.5);
            this.xScale.domain(this.newDomain.domain);
            newDots.transition()
                    .duration(1000)
                    .attr("cx", this.xCoord)
                    .attr("cy", this.yCoord);

        }

        if (this.updateMode.type === this.UPDATE_PATTERN.fade) {
            newDots.remove();
            newDots = this.focus.selectAll("." + this.chartID + this.dotType);
            let entry = newDots.data(data.filter(d => d[1] == null ? null : d))
                    .enter()
                    .append("circle")
                    .attr("class", this.chartID + this.dotType)
                    .attr("cx", this.xCoord)
                    .attr("cy", this.yCoord)
                    .attr("stroke", this.strokeColor)
                    .attr("r", 0);
            entry.transition()
                    .duration(1000)
                    .attr("r", 3.5);
        }

        if (this.updateMode.type === this.UPDATE_PATTERN.generalUpdate) {
            newDots.remove();
            newDots = this.focus.selectAll("." + this.chartID + this.dotType);
            let entry = newDots.data(data.filter(d => d[1] == null ? null : d))
                    .enter();
            this.addDots(entry, this.dotType, false);
        }


    }
}

class InvisibleDots extends UpdateableDots {
    constructor(dotType, clickable, focus, chartID, data, updateMode, UPDATE_PATTERN, xScale, xCoord, yCoord, oldXScale, newDomain,
            strokeColor, touchstart) {
        super(dotType, clickable, focus, chartID, data, updateMode, UPDATE_PATTERN, xScale, xCoord, yCoord, oldXScale, newDomain,
                strokeColor, touchstart);
    }
    update(data) {
        let newInvisibles = this.focus.selectAll("." + this.chartID + this.dotType);
        newInvisibles.remove();
        newInvisibles = this.focus.selectAll("." + this.chartID + this.dotType)
                .data(data.filter(d => d[1] === null ? null : d))
                .enter();
        this.addDots(newInvisibles, this.dotType, true);

    }
}

