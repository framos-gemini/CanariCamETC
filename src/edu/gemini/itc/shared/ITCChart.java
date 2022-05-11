package edu.gemini.itc.shared;


import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.*;
import org.jfree.data.*;
import org.jfree.chart.axis.NumberAxis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.Legend;
import org.jfree.chart.Marker;
import org.jfree.chart.plot.PlotOrientation;

public class ITCChart {
    /**
	 * @uml.property  name="chartTitle"
	 */
    private String chartTitle;
	/**
	 * @uml.property  name="xAxisLabel"
	 */
	private String xAxisLabel;
	/**
	 * @uml.property  name="yAxisLabel"
	 */
	private String yAxisLabel;
    /**
	 * @uml.property  name="seriesData"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="org.jfree.data.XYSeries"
	 */
    private XYSeriesCollection seriesData = new XYSeriesCollection();
    /**
	 * @uml.property  name="chart"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JFreeChart chart;
    
    public ITCChart() {
        this("DefaultTitle","xAxis","yAxis");
    }
    
    public ITCChart(java.lang.String chartTitle,
    java.lang.String xAxisLabel,
    java.lang.String yAxisLabel) {
        this.chartTitle=chartTitle;
        this.xAxisLabel=xAxisLabel;
        this.yAxisLabel=yAxisLabel;
        chart = ChartFactory.createXYLineChart(this.chartTitle,this.xAxisLabel,this.yAxisLabel,	this.seriesData, PlotOrientation.VERTICAL, true, false, false);
        chart.getLegend().setAnchor(Legend.NORTH);
        chart.setBackgroundPaint(java.awt.Color.white);
    }
    
    public void addArray(double data[][], java.lang.String seriesName)
    {
    		XYSeries newSeries = new XYSeries(seriesName);
    		for(int i = 0; i<data[0].length; i++)
    		{
                      if(data[0][i] >0)   ///!!!!keeps negative x values from being added to a chart!!!!
    			newSeries.add(data[0][i],data[1][i]);
    		}
    
    		this.seriesData.addSeries(newSeries);
                
    }
    
    public void addArray(double data[][], java.lang.String seriesName, java.awt.Color color)
    {
        this.addArray(data, seriesName);
        
        ((XYPlot)chart.getPlot()).getRenderer().setSeriesPaint(this.seriesData.getSeriesCount()-1,color);
    }
    
    public int findSeriesInCollection(java.lang.String seriesName) {
        XYSeries tempSeries;
        List seriesValues = this.seriesData.getSeries();
        Iterator seriesNames = seriesValues.iterator();
        while (seriesNames.hasNext()){
            tempSeries = (XYSeries)seriesNames.next();
            if(tempSeries.getName().equals(seriesName)){
                return seriesValues.indexOf(tempSeries);
            }
        }
        return -1;
        
        
    }
    
    public void addTitle(java.lang.String chartTitle) {
        this.chartTitle=chartTitle;
        chart.setTitle(this.chartTitle);
    }
    
    public void addxAxisLabel(java.lang.String xAxisLabel) {
        this.xAxisLabel=xAxisLabel;
        chart.getXYPlot().getDomainAxis().setLabel(xAxisLabel);
    }
    
    public void addyAxisLabel(java.lang.String yAxisLabel) {
        this.yAxisLabel=yAxisLabel;
        chart.getXYPlot().getRangeAxis().setLabel(yAxisLabel);
    }
    
    public void autoscale() {
        chart.getXYPlot().getDomainAxis().setAutoRange(true);
        chart.getXYPlot().getRangeAxis().setAutoRange(true);
    }
    
    public void setDomainMinMax(double lower, double upper) {
        chart.getXYPlot().getDomainAxis().setRange(lower,upper);
    }
    
    public void setRangeMinMax(double lower, double upper) {
        chart.getXYPlot().getRangeAxis().setRange(lower, upper);
    }
    
    public void addHorizontalLine(double value) {
        //chart.getXYPlot().addHorizontalLine(new Double(value));
        chart.getXYPlot().addDomainMarker(new Marker(value));
        
    }
    
    public void flush() {
        seriesData.removeAllSeries();
        this.chartTitle="DefaultTitle";
        this.xAxisLabel="xAxis";
        this.yAxisLabel="yAxis";
    }
    
    public java.awt.image.BufferedImage getBufferedImage() throws Exception {
        BufferedImage image =  chart.createBufferedImage(675,500);
        return image;
    }
    
    public void saveChart(java.lang.String chartName) throws Exception {
        ChartUtilities.saveChartAsPNG(new File(chartName), chart,
        675,500);
    }
    
     private static JFreeChart createXYLineChart(String title,
                                               String xAxisLabel,
                                               String yAxisLabel,
                                               XYDataset dataset,
                                               PlotOrientation orientation,
                                               boolean legend) {
                                                                                                        
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        xAxis.setAutoRangeIncludesZero(false);
        NumberAxis yAxis = new NumberAxis(yAxisLabel);
        XYItemRenderer renderer = new StandardXYItemRenderer(StandardXYItemRenderer.LINES);
        XYPlot plot = new ITCChartXYPlot(dataset, xAxis, yAxis, renderer);
        plot.setOrientation(orientation);
        
                                                                                                        
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);
                                                                                                        
        return chart;
                                                                                                        
    }

}
