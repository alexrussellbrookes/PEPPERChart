/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class ChartArrayUtils {
    static makeCreateSet(createSet, refreshSet, updateSet, xScale, yScale, selection) {
        for (let i = 0; i < createSet.length; i += 1) {
            createSet[i].create(xScale, yScale, selection);
            refreshSet.push(createSet[i]);
            updateSet.push(createSet[i]);
        }
    }
}

