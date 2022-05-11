// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
//
package edu.gemini.itc.shared;

import edu.gemini.itc.shared.TransmissionElement;
import edu.gemini.itc.shared.Instrument;

/**
 * This represents the transmission of the optics native to the camera.
 */
public class FixedOptics extends TransmissionElement {
    //private static final String FILENAME = "fixed_optics" + Instrument.getSuffix() + "_GEMINI";

    private static final String FILENAME =
            "fixed_optics" + Instrument.getSuffix();
    
    public FixedOptics(String directory, String prefix) throws Exception {
        super(directory + prefix + FILENAME);

    }

    public String toString() {
        return "Optics: Fixed Optics";
    }

}
