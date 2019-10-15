/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class XAxis {
    constructor(selection, axisID, height, width, xAxis, fontSize, xAxisLabel, ticksCall) {
        this.xAxis = xAxis;
        this.axisObj = selection.append("g")
                .attr("class", "axis axis--x")
                .attr("id", axisID)
                .attr("transform", `translate(0, ${height})`)
                .call(this.xAxis)
                .attr("font-size", fontSize);
        if (ticksCall) {
            this.axisObj.call(ticksCall);
        }
        if (xAxisLabel) {
            selection.append("text")
                    .attr("fill", "grey")
                    .attr("transform", `translate(${width}, ${height - 6})`)
                    .attr("dx", "1em")
                    .attr("font-size", fontSize)
                    .attr("text-anchor", "end")
                    .text(xAxisLabel);
        }
    }
    
    getSelection() {
        return this.axisObj;
    }

    refresh(data) {
        this.axisObj.call(this.xAxis);
    }
}

class YAxis {
    constructor(selection, yAxis, fontSize, yAxisLabel) {
        let axisY = selection.append("g")
                .attr("class", "axis axis--y")
                .call(yAxis)
                .attr("font-size", fontSize);
        if (yAxisLabel) {
            selection.append("text")
                    .attr("fill", "grey")
                    .attr("y", -6)
                    .attr("x", 0)
                    .attr("dx", "2em")
                    .attr("dy", "-0.5em")
                    .attr("font-size", fontSize)
                    .attr("text-anchor", "end")
                    .text(yAxisLabel);
        }
        return axisY;
    }
}