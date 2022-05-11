// Copyright 2001 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
package edu.gemini.itc.shared;

/**
 * The GaussianMorphology concreate class implements the operations that all
 * that are defined in it's abstract parent class Morphology2d.
 *
 */
public class GaussianMorphology extends Morphology3D {

    /**
	 * @uml.property  name="sigma"
	 */
    private double sigma;

    /**
     * Constructor for the Gaussian Morphology
     * @param sourceSize Allows the source size in arcsec to be set at constuction
     */

    public GaussianMorphology(double sourceSize) {
        sigma = sourceSize / 2.355;
    }

    /**
	 * We should provide methods that allow the calculation of integrals for square, circular.
	 * @uml.property  name="_2D_SQUARE_INTEGRAL" multiplicity="(0 -1)" dimension="1"
	 */

    //////////SQUARE//////////////

    // Values of a 2-D gaussian integral over a square region.
    private double[] _2D_SQUARE_INTEGRAL =
            {0, 0.031, 0.118, 0.245, 0.393, 0.542, 0.675, 0.784, 0.865, 0.920, 0.956};

    // This is a table of the integral of a 2-D gaussian over a square region.
    // X is the length of the square as a fraction of sigma.
    // Y is the value of the double integral.
    /**
	 * @uml.property  name="_2d_square_integralTable"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private DefaultSampledSpectrum _2d_square_integralTable = new
            DefaultSampledSpectrum(_2D_SQUARE_INTEGRAL, 0, 0.5);

    /**
     * This method returns the value of a 2-D circularly-symmetric gaussian
     * over a square region centered about the mean.
     * @param sigmaFraction The length of the square as a fraction of sigma.
     */
    public double get2DSquareIntegral(double sigmaFraction) {
        // Use linear interpolation on the table of pre-calculated results.
        return _2d_square_integralTable.getY(sigmaFraction);
    }

    /**
     * This Method returns the value of a 2-D circularly-symmetric gaussian
     * over a square region that is arbitrarily placed on the gaussian.
     * @param xMin The coordinate of the minimum X position.
     * @param xMax The coordinate of the maximum X position.
     * @param yMin The coordinate of the minimum Y position.
     * @param yMax The coordinate of the maximum Y position.
     */

    public double get2DSquareIntegral(double xMin, double xMax, double yMin, double yMax) {
        double sgConst = Math.sqrt(2) / (2 * sigma);
        try {
            ErrorFunction erf = new ErrorFunction();

            return .25 * erf.getERF(yMax * sgConst) * erf.getERF(xMax * sgConst) -
                    .25 * erf.getERF(yMax * sgConst) * erf.getERF(xMin * sgConst) -
                    .25 * erf.getERF(yMin * sgConst) * erf.getERF(xMax * sgConst) +
                    .25 * erf.getERF(yMin * sgConst) * erf.getERF(xMin * sgConst);
        } catch (Exception e) {
            System.out.println("Could not Create Error Function;");
            return 0;
        }
    }


    //////////END SQUARE//////////////////


    //////////BEGIN CIRCULAR/////////////

    /**
	 * @param sigma
	 * @uml.property  name="sigma"
	 */
    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    /**
	 * @return
	 * @uml.property  name="sigma"
	 */
    public double getSigma() {
        return this.sigma;
    }

    public void accept(MorphologyVisitor v) throws Exception {
        v.visitGaussian(this);
    }


}
