// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: TelescopeApertureVisitor.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.operation;

import edu.gemini.itc.shared.SampledSpectrumVisitor;
import edu.gemini.itc.shared.SampledSpectrum;

/**
 * This class encapsulates information about the telescope aperture.
 * Other classes can refer to this one for aperture information.
 * It also functions as a visitor to a SED.
 * The main function is to convert a SED from "flux per m^2" to absolute flux.
 */
public final class TelescopeApertureVisitor implements SampledSpectrumVisitor {
    /**
     * Area of 8 meter-diameter mirror minus 1 meter hole in middle.
     * (radius = 4 meters)
     */
    public static final double TELESCOPE_APERTURE = (Math.PI * (5.2 * 5.2)) - (Math.PI * (0.8 * 0.8));

    /**
     * Current values in the SED are probably "per m^2".
     * This operation multiplies each spectrum value by the telescope area.
     */
    public void visit(SampledSpectrum sed) {
        sed.rescaleY(TELESCOPE_APERTURE);
    }

    public String toString() {
        return "TelescopeAperture " + TELESCOPE_APERTURE;
    }
}
