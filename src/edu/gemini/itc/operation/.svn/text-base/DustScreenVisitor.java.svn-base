// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: DustScreenVisitor.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.operation;

import edu.gemini.itc.shared.SampledSpectrumVisitor;
import edu.gemini.itc.shared.SampledSpectrum;

/**
 * This visitor acts as a dust screen for the spectrum.
 */
public class DustScreenVisitor implements SampledSpectrumVisitor {

    /**
	 * @uml.property  name="_av"
	 */
    private double _av = 0;  // Visual extinction

    /**
     * @param av visual extinction used in dust extinction law
     */
    public DustScreenVisitor(double av) {
        _av = av;
    }

    /**
     * Performs the extinction on specified spectrum.
     */
    public void visit(SampledSpectrum sed) {
        for (int i = 0; i < sed.getLength(); i++) {
            sed.setY(i, sed.getY(i) * Math.pow(10, (-0.4 * _av)));
        }
        return;
    }

    public String toString() {
        return "Dust Screen Visual Extinction (Av) = " + _av;
    }
}
