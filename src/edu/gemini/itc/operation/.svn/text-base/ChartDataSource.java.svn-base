// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id ChartDataSource.java,v 1.2 1999/10/14 16:37:00 cvs-tuc Exp $
//

// No se está utilizando en el proyectooooooooooooo

package edu.gemini.itc.operation;

import com.klg.jclass.chart.ChartDataModel;
import com.klg.jclass.chart.LabelledChartDataModel;

public class ChartDataSource implements ChartDataModel, LabelledChartDataModel {
    /**
	 * @uml.property  name="_xvalues" multiplicity="(0 -1)" dimension="1"
	 */
    private double _xvalues[];
    /**
	 * @uml.property  name="_yvalues" multiplicity="(0 -1)" dimension="2"
	 */
    private double _yvalues[][];
    /**
	 * @uml.property  name="_sedTitle"
	 */
    private String _sedTitle;
	/**
	 * @uml.property  name="yaxisTitle"
	 */
	private String yaxisTitle;
    /**
	 * @uml.property  name="_xMin"
	 */
    private double _xMin;
	/**
	 * @uml.property  name="_xMax"
	 */
	private double _xMax;
    /**
	 * @uml.property  name="pointLabels"
	 */
    protected String pointLabels[];
    /**
	 * @uml.property  name="seriesLabels"
	 */
    protected String seriesLabels[] = new String[5];
    /**
	 * @uml.property  name="seriesIndex"
	 */
    private int seriesIndex = 0;


    public void setSpectrum(double xvalues[], double yvalues[][],
                            String sedTitle, String seriesLabel) {

        this._yvalues = new double[5][yvalues[0].length];


        this._xvalues = xvalues;
        //setXMin(_xvalues[0]);
        //setXMax(_xvalues[_xvalues.length-1]);
        for (int i = 0; i <= yvalues[0].length - 1; i++) {
            this._yvalues[seriesIndex][i] = yvalues[0][i];
        }

        seriesLabels[seriesIndex] = seriesLabel;

        _sedTitle = sedTitle;
    }

    public void addSpectrum(double yvalues[][],
                            String seriesLabel) {
        seriesIndex++;

        for (int i = 0; i <= yvalues[0].length - 1; i++) {
            //  System.out.println("i: " + yvalues[0].length);
            this._yvalues[seriesIndex][i] = yvalues[0][i];
            //System.out.println("yvals: " + yvalues[0][i]);
        }
        //	System.out.println("hi");
        //	this._yvalues[seriesIndex][]=yvalues;
        seriesLabels[seriesIndex] = seriesLabel;
        //  System.out.println(seriesLabel);
    }

    public void resetDataSource() {
        seriesIndex = 0;
    }

    public double[] getXSeries(int index) {
        return _xvalues;
    }

    public double[] getYSeries(int index) {
        return _yvalues[index];
    }

    public int getNumSeries() {
        return seriesIndex + 1;
    }//_yvalues.length; }

    public String getDataSourceName() {
        return "";
    }

    public String getDataSourceN() {
        return _sedTitle;
    }

    //public double getXMin() {return _xvalues[0];}

    //public double getXMax() {return _xvalues[_xvalues.length-1];}

    public double getXMin() {
        return _xMin;
    }

    public double getXMax() {
        return _xMax;
    }

    public void setXMin(double _xMin) {
        this._xMin = _xMin;
    }

    public void setXMax(double _xMax) {
        this._xMax = _xMax;
    }

    /**
	 * @return
	 * @uml.property  name="pointLabels"
	 */
    public String[] getPointLabels() {
        return pointLabels;
    }

    /**
	 * @return
	 * @uml.property  name="seriesLabels"
	 */
    public String[] getSeriesLabels() {
        return seriesLabels;
    }

    /**
	 * @return
	 * @uml.property  name="yaxisTitle"
	 */
    public String getYaxisTitle() {
        return yaxisTitle;
    }

    /**
	 * @param yaxisTitle
	 * @uml.property  name="yaxisTitle"
	 */
    public void setYaxisTitle(String yaxisTitle) {
        this.yaxisTitle = yaxisTitle;
    }
}
