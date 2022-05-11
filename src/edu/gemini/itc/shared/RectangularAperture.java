// Copyright 2001 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
package edu.gemini.itc.shared;

import java.util.List;
import java.util.ArrayList;

/**
 * This is the concrete class is one type of aperture supported by the itc
 * The class also plays the role of Visitor to a morphology.  This allows
 * the class to calculate different values of the SourceFraction for different
 * types of Morphologies.
 */
public class RectangularAperture extends ApertureComponent {
    /**
	 * @uml.property  name="sourceFraction"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Double"
	 */
    List sourceFraction = new ArrayList();
    /**
	 * @uml.property  name="iFUlenX"
	 */
    double IFUlenX;
	/**
	 * @uml.property  name="iFUlenY"
	 */
	double IFUlenY;
	/**
	 * @uml.property  name="iFUposX"
	 */
	double IFUposX;
	/**
	 * @uml.property  name="iFUposY"
	 */
	double IFUposY;

    public RectangularAperture(double IFUlenX, double IFUlenY, double IFUposX, double IFUposY) {
        this.IFUlenX = IFUlenX;
        this.IFUlenY = IFUlenY;
        this.IFUposX = IFUposX;
        this.IFUposY = IFUposY;
    }


    //Methods for visiting a Morphology
    public void visitGaussian(Morphology3D morphology) {
        double xLower = IFUposX - IFUlenX / 2;
        double xUpper = IFUposX + IFUlenX / 2;
        double yLower = IFUposY - IFUlenY / 2;
        double yUpper = IFUposY + IFUlenY / 2;

        double fractionOfSourceInAperture;

        fractionOfSourceInAperture = morphology.get2DSquareIntegral(xLower, xUpper, yLower, yUpper);

        sourceFraction.add(new Double(fractionOfSourceInAperture));


    }

    public void visitUSB(Morphology3D morphology) {
        // Might work, not sure.
        sourceFraction.add(new Double(IFUlenX * IFUlenY * Math.PI / 4));
    }

    public void visitExponential(Morphology3D morphology) {
        // not implemented
    }

    public void visitElliptical(Morphology3D morphology) {
        // not implemented
    }

    //Method for returning the Sourcefraction for this Aperture
    public List getFractionOfSourceInAperture() {
        return sourceFraction;
    }

}
