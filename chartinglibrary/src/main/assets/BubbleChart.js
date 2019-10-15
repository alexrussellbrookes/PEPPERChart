function BubbleChart() {
    let totalWidth = 960, totalHeight = 500, mLeft = 100, mRight = 100, mTop = 200, mBottom = 50;
    let width;
    let fontSize = "18";
    let componentType = "circle";
    let timeScale = true;
    let xDomainSpec = null;
    let yDomainSpec = null;
    let totalCount = 0;
    let xAxisLabel = null;
    let yAxisLabel = null;
    let bubbleColor = "#BFF0FE";
    let yAligned = true;
    let yTicks = true;
    let bubbleProp = true;
    let bubbleID = "bubble";
    let textFill = false;
    let ticks = 4;
    let axisY;
    let axisX;
    let height;
    let g;
    let bar;
    let updateSet = [];
    let createSet = [];
    let xScale = d3.scaleTime();
    let yScale = d3.scaleLinear();
    let zScale = d3.scaleLinear();
    let xAxis = d3.axisBottom();
    let yAxis = d3.axisLeft();
    const SECOND = 1000;
    const MINUTE = 60 * SECOND;
    const HOUR = 60 * MINUTE;

    function chart(selection) {
        selection.each(function (data) {
            data = formatData(data);
            totalCount = getTotalCount(data, totalCount);

            let xMax = getXMax(data);
            let xMin = getXMin(data);
            let zMax = getZMax(data);
            let yMin = getYMin(data);
            let yMax = getYMax(data);

            let margin = {top: mTop, right: mRight, bottom: mBottom, left: mLeft};
            width = totalWidth - margin.left - margin.right;
            height = totalHeight - margin.top - margin.bottom;
            formatXDomain(xScale, xDomainSpec, xMin, xMax);
            xScale.range([0, width]);
            formatYDomain(yScale, yDomainSpec, yMin, yMax);
            yScale.range([height, 0]);
            zScale.domain([0, zMax])
                    .range([0, 45]);

            let svg = d3.select(this).selectAll("svg").data([data]);
            let gEnter = svg.enter().append("svg");
            gEnter.attr("width", totalWidth)
                    .attr("height", totalHeight);

            PathFunctions.makeClipPath(gEnter, bubbleID, width, height);

            g = gEnter.append("g")
                    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

            xAxis = (d3.axisBottom(xScale).ticks(4));
            yAxis = d3.axisLeft(yScale);
            axisX = new XAxis(g, "", height, width, xAxis, fontSize, xAxisLabel).getSelection();
            axisY = new YAxis(g, yAxis, fontSize, yAxisLabel)
            yTicks ? axisY.call(yAxis) : axisY.call((yAxis).ticks(0));

            //Makes either a Rectangle chart or a Bubble chart.
            makeChart(data, componentType);
            if (textFill) {
                makeChart(data, "text");
            }
            
            for (var i = 0; i < createSet.length; i += 1) {
                createSet[i].create(xScale, yScale, g);
                updateSet.push(createSet[i]);
            }
        });
    }
    
    function formatData(data) {
        data = data.map(function (d, i) {
            return [xValue.call(data, d, i), yValue.call(data, d, i), zValue.call(data, d, i), label.call(data, d, i)];
        });
        return data;
    }
    function getTotalCount(data, totalCount) {
        data.forEach(function (d) {
            totalCount += d[1];
        });
        return totalCount;
    }
    
    function formatYDomain(yScale, yDomainSpec, yMin, yMax) {
        yScale.domain(yDomainSpec ? yDomainSpec : [0, yMax]);
    }
    
    function formatXDomain(xScale, xDomainSpec, xMin, xMax) {
        xScale.domain(xDomainSpec ? xDomainSpec : [xMin, xMax]);
    }
    
    function xValue(d) {
        return d[0];
    }
    function yValue(d) {
        return d[1];
    }
    function zValue(d) {
        return d[2];
    }
    function label(d) {
        return d[3];
    }

    function getXMax(data) {
        return d3.max(data, d => d[0]);
    }

    function getXMin(data) {
        return d3.min(data, d => d[0]);
    }

    function getZMax(data) {
        return d3.max(data, d => d[2]);
    }

    function getYMin(data) {
        return d3.min(data, d => d[1]);
    }

    function getYMax(data) {
        return d3.max(data, d => d[1]);
    }

    function makeChart(data, markType) {
        bar = g.append("g");
        let barSelection = bar.selectAll(markType)
                .data(data, d => d[0])
        barSelection = barSelection.enter();
        makeMarkType(markType, barSelection);

        bar.update = function (data) {
            let blankSelection = this.selectAll(markType).filter(d => d[1] == null);
            blankSelection.remove();
            let updateMarks = this.selectAll(markType)
                    .data(data, d => d[0]);
            let updateSelection = updateMarks.enter();
            makeMarkType(markType, updateSelection);
            updateMarks.exit().remove();
            updateMarks = this.selectAll(markType);
            doAnimation(markType, updateMarks);
        };
        updateSet.push(bar);
    }

    function makeMarkType(markType, selection) {
        if (markType === "circle") {
            makeBubble(selection);
        } else if (markType === "rect") {
            makeRect(selection);
        } else if (markType === "text") {
            makeText(selection);
        }
    }

    function doAnimation(markType, marks) {
        if (markType === "circle") {
            doBubbleAnimation(marks)
        } else if (markType === "rect") {
            doRectAnimation(marks);
        } else if (markType === "text") {
            doTextAnimation(marks);
        }
    }

    function doRectAnimation(selection) {
        selection.transition()
                .duration(1000)
                .attr("x", d => getRectX(d))
                .attr("y", d => getRectY(d))
                .attr("height", d => getRectHeight(d));
    }

    function doBubbleAnimation(selection) {
        selection.transition()
                .duration(1000)
                .attr("cx", d => getBubbleX(d))
                .attr("cy", d => getBubbleY(d))
                .attr("r", d => getBubbleR(d));
    }

    function doTextAnimation(selection) {
        selection.transition()
                .duration(1000)
                .attr("x", d => getTextX(d))
                .attr("y", d => getTextY(d));
    }

    function makeBubble(selection) {
        selection.append("circle")
                .attr("class", bubbleID + "bubble")
                .attr("fill", bubbleColor)
                .attr("cx", d => getBubbleX(d))
                .attr("cy", d => getBubbleY(d))
                .attr("r", d => getBubbleR(d));
    }

    function getBubbleX(d) {
        return getRectX(d);
    }

    function getBubbleY(d) {
        return (yAligned ? yScale(d[1]) : height / 2);
    }

    function getBubbleR(d) {
        return bubbleProp ? zScale(d[2]) : d[2] == null ? 0 : 30;
    }

    function getTextX(d) {
        return xScale(d[0]);
    }

    function getTextY(d) {
        return (d[1] == null ? null : yAligned ? yScale(d[1]) : height / 2);
    }

    function getRectX(d) {
        return xScale(d[0]);
    }

    function getRectY(d) {
        if (yAligned) {
            return yScale(d[1]);
        }
        return height / 2 + 10;
    }

    function getRectHeight(d) {
        if (d[1] === null || d[1] === 0) {
            return 0;
        }
        if (!yAligned) {
            return  height / 2 - 10; 
        }
        return height - yScale(d[1]);
    }

    function makeText(selection) {
        selection.append("text")
                .attr("x", d => getTextX(d))
                .attr("y", d => getTextY(d))
                .attr("text-anchor", componentType === "rect" ? "left" : "middle")
                .attr("font-size", fontSize)
                .html(d => d[3]);
    }

    function makeRect(selection) {
        selection.append("rect")
                .attr("class", bubbleID + "bubble")
                .attr("fill", bubbleColor)
                .attr("x", d => getRectX(d))
                .attr("y", d => getRectY(d))
                .attr("width", d => getRectWidth(d))
                .attr("height", d => getRectHeight(d));
    }

    function getRectWidth(d) {
        let date = new Date(d[0].getTime() + HOUR);
        let endPosition = xScale(date);
        let width = endPosition - xScale(d[0]) - 5;
        return (d[1] === 0 ? 0 : width);
    }

    function update(data) {
        totalCount = 0;
        data = formatData(data);
        totalCount = getTotalCount(data, totalCount);
        let xMax = getXMax(data);
        let xMin = getXMin(data);
        let yMin = getYMin(data);
        let yMax = getYMax(data);
        formatXDomain(xScale, xDomainSpec, xMin, xMax);
        formatYDomain(yScale, yDomainSpec, yMin, yMax);
       
        axisX.transition()
                .duration(1000)
                .call(xAxis);
        
        for (let i = 0; i < updateSet.length; i += 1) {
            updateSet[i].update ? updateSet[i].update(data) : updateSet[i].refresh(data);
        }
    }

    chart.x = function (_) {
        xValue = _;
    };
    chart.y = function (_) {
        yValue = _;
    };
    chart.z = function (_) {
        zValue = _;
    };
    chart.label = function (_) {
        label = _;
    };
    chart.width = function (_) {
        if (!arguments.length) 
            return width;
        totalWidth = +_;
    };
    chart.height = function (_) {
        totalHeight = +_;
    };
    chart.fontSize = function (_) {
        if (!arguments.length)
            return fontSize;
        fontSize = _;
    };
    chart.mTop = function (_) {
        mTop = +_;
    };
    chart.mBottom = function (_) {
        mBottom = +_;
    };
    chart.mLeft = function (_) {
        mLeft = +_;
    };
    chart.mRight = function (_) {
        mRight = +_;
    };
    chart.componentType = function(_){
        componentType = _;
    };
    chart.bubbleColor = function (_) {
        bubbleColor = _;
    };
    chart.xAxisLabel = function (_) {
        xAxisLabel = _;
    };
    chart.yAxisLabel = function (_) {
        yAxisLabel = _;
    };
    chart.yAligned = function (_) {
        yAligned = _;
    };
    chart.xDomainSpec = function (_) {
        xDomainSpec = _;
    };
    chart.yDomainSpec = function (_) {
        yDomainSpec = _;
    };
    chart.yTicks = function (_) {
        yTicks = _;
    };
    chart.ticks = function (_) {
        ticks = _;
    };
    chart.bubbleProp = function (_) {
        bubbleProp = _;
    };
    chart.bubbleID = function (_) {
        bubbleID = _;
    }; 
    chart.totalCount = function(){
        return totalCount;
    };
    chart.textFill = function (_) {
        textFill = _;
    };
    chart.gEnter = function () {
        return g;
    };
    chart.yScale = function (input) {
        return yScale(input);
    };
    chart.xScale = function (input) {
        return xScale(input);
    };
    chart.timeScale = function (_) {
        timeScale = _;
        xScale = timeScale ? d3.scaleTime() : d3.scaleLinear();
    };
    chart.addCreateItem = function (_) {
        createSet.push(_);
    };
    chart.addUpdateItem = function (_) {
        updateSet.push(_);
    };
    chart.update = function (data) {
        update(data);
    };
    return chart;
}