/*
 * ITCChartPlot.java
 *
 * Created on January 21, 2004, 12:19 PM
 */

package edu.gemini.itc.shared;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.*;
import org.jfree.data.*;
import org.jfree.chart.axis.*;

/**
 *
 * @author  bwalls
 */
public class ITCChartXYPlot extends org.jfree.chart.plot.XYPlot {
    
    /** Creates a new instance of ITCChartPlot */
    /** With this class you loose the ability to use secondary datasets.  */
    public ITCChartXYPlot() {
        super();
    }
    
    public ITCChartXYPlot(XYDataset dataset, ValueAxis domainAxis, ValueAxis rangeAxis, XYItemRenderer renderer) {
        super(dataset, domainAxis, rangeAxis, renderer);
    }
    
    public org.jfree.chart.LegendItemCollection getLegendItems() {
        LegendItemCollection result = new LegendItemCollection();
        
        // get the legend items for the main dataset...
        XYDataset dataset1 = getDataset();
        if (dataset1 != null) {
            XYItemRenderer renderer = getRenderer();
            if (renderer != null) {
                int seriesCount = dataset1.getSeriesCount();
                LegendItem item = renderer.getLegendItem(0, 0);
                    result.add(item);
                LegendItem item2 = renderer.getLegendItem(0, 1);
                    result.add(item2);
            }
        }
        
        
        return result;
        
    }
    
    
    
}
