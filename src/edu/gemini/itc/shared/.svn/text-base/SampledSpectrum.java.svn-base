// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: SampledSpectrum.java,v 1.4 2004/02/13 13:00:59 bwalls Exp $
//
package edu.gemini.itc.shared;

/**
 * The SampledSpectrum interface represents a spectral energy distribution.
 * A SampledSpectrum can conceptually be thought of as a 2-D spectrum
 * (data points on axes of real numbers) where the data points occur
 * at regular intervals on the x axis.
 * This interface provides functionality for defining and manipulating a
 * SampledSpectrum.
 * Units are not stored for either axis so the client must know from context.
 * The SampledSpectrum plays the rold of Element in a visitor pattern.
 * This pattern is used to separate operations from the SampledSpectrum
 * elements.
 */
public interface SampledSpectrum extends ArraySpectrum {
    /** @return array of spectrum y values */
    public double[] getValues();

    /** @return x sample size (bin size) */
    public double getSampling();

    /**
     * Sets all these SampledSpectrum parameters.
     * I don't like methods that set several things at once, but I
     * inherited this code, so I won't change it yet.
     */
    public abstract void reset(double[] flux, double wavelengthStart,
                               double wavelengthInterval);
    
    public abstract void trim(double wavelengthStart, double wavelengthEnd);
    

    /** @return String of all the Values */
    public String printSpecAsString();
}
