// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: Gaussian.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

/**
 * This class supplies utility routines for dealing with gaussian curves.
 */
public class Gaussian {
    // Values of a 2-D gaussian integral over a square region.
    private static final double[] _2D_INTEGRAL_UF =
           {0, 0.031, 0.118, 0.245, 0.393, 0.542, 0.675, 0.784, 0.865, 0.920, 0.956};
    private static final double[] _2D_INTEGRAL =
    	   {0, 0.031, 0.118, 0.245, 0.393, 0.542, 0.675, 0.784, 0.865, 0.920, 0.956, 0.977, 0.989, 0.995, 0.998, 0.999, 1.00};

    // This is a table of the integral of a 2-D gaussian over a square region.
    // X is the length of the square as a fraction of sigma.
    // Y is the value of the double integral.
    private static DefaultSampledSpectrum _2d_integralTable_uf = new DefaultSampledSpectrum(_2D_INTEGRAL_UF, 0, 0.5);
    private static DefaultSampledSpectrum _2d_integralTable = new DefaultSampledSpectrum(_2D_INTEGRAL, 0, 0.25);

    /**
     * This method returns the value of a 2-D circularly-symmetric gaussian
     * over a square region centered about the mean.
     * @param sigmaFraction The length of the square as a fraction of sigma.
     */
    public static double get2DIntegral(double sigmaFraction) {
        // Use linear interpolation on the table of pre-calculated results.
        return _2d_integralTable.getY(sigmaFraction);
    }
    
    public static double get2DIntegralUF(double sigmaFraction) {
        // Use linear interpolation on the table of pre-calculated results.
        return _2d_integralTable_uf.getY(sigmaFraction);
    }
}
