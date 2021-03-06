// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ArraySpectrum.java,v 1.4 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

/**
 * This interface represents a 2-D spectrum.  The x and y axes are the
 * real numbers (doubles).  This spectrum is abstracted as an array
 * of data points are not necessarily at regular x intevals, but
 * with increasing x.  (i.e. if you connect the dots, this is a function).
 */
public interface ArraySpectrum extends Spectrum, Cloneable {
    // Adds the following methods to the Spectrum interface

    /** Returns x value of specified data point. */
    double getX(int index);

    /** Returns y value of specified data point. */
    double getY(int index);

    /** @return number of bins in the histogram (number of array elements) */
    int getLength();

    /** Returns the index of the data point with largest x value less than x */
    int getLowerIndex(double x);

    /** Applys a wavelength correction to the sed */
    void applyWavelengthCorrection();

    /** Sets Y value at specified index. */
    void setY(int index, double y);

    /** Rescales X axis by specified factor. */
    void rescaleX(double factor);

    /** Rescales Y axis by specified factor. */
    void rescaleY(double factor);

    /** Smooths the Y axis with a smoothing element */
    void smoothY(int smoothing_element);

    /**
     * Returns the sum of values in the ArraySpectrum in
     * the specified range.
     * @throws Exception If either limit is out of range.
     */
    double getSum(int indexStart, int indexEnd) throws Exception;

    /**
     * Returns the integral of values in the ArraySpectrum in
     * the specified range.
     * @throws Exception If either limit is out of range.
     */
    double getIntegral(int indexStart, int indexEnd) throws Exception;

    /**
     * Returns the average of values in the SampledSpectrum in
     * the specified range.
     * If either limit is out of range, the average is not specified.
     */
    double getAverage(int indexStart, int indexEnd) throws Exception;

    /**
     * This returns a 2d array of the SED data used to chart the SED
     * using JClass Chart.  The array has the following dimensions
     *    double data[][] = new double[2][getLength()];
     * data[0][i] = x values
     * data[1][i] = y values
     */
    double[][] getData();

    /**
     * This returns a 2d array of the SED data used to chart the SED
     * using JClass Chart.  The array has the following dimensions
     *    double data[][] = new double[2][getLength()];
     * data[0][i] = x values
     * data[1][i] = y values
     *
     * @param maxIndex data returned up to maximum specified
     * x bin
     */
    double[][] getData(int maxIndex);
}
