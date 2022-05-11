// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: RedshiftVisitor.java,v 1.3 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.operation;

import edu.gemini.itc.shared.SampledSpectrumVisitor;
import edu.gemini.itc.shared.SampledSpectrum;

/**
 * This visitor performs a redshift on the spectrum.
 */
public class RedshiftVisitor implements SampledSpectrumVisitor {
    /**
     * For efficiency, no shift will be performed unless z is
     * larger than this value
     */
    public static final double MIN_SHIFT = 0.0001;

    /**
	 * @uml.property  name="_z"
	 */
    private double _z = 0;  // z = v / c

    /**
     * @param z redshift = velocity / c
     */
    public RedshiftVisitor(double z) {
        _z = z;
    }

    /**
     * Performs the redshift on specified spectrum.
     */
    public void visit(SampledSpectrum sed) {
        // only shift if greater than MIN_SHIFT
        if (getShift() <= MIN_SHIFT) return;  // No scaling to be done.

        sed.rescaleX(1.0 + getShift());
        return;
    }

    /**
     * @return the redshift
     */
    public double getShift() {
        return _z;
    }

    /**
     * Sets the redshift.
     */
    public void setShift(double z) {
        _z = z;
    }

    public String toString() {
        return "Redshift z = " + getShift();
    }
}
