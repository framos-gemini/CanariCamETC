// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
//
package edu.gemini.itc.shared;

import edu.gemini.itc.shared.TransmissionElement;
import edu.gemini.itc.shared.Instrument;

public class Detector extends TransmissionElement {
    //private static final String FILENAME = "ccd_red" + Instrument.getSuffix();
    /**
	 * @uml.property  name="_detectorPixels"
	 */
    private int _detectorPixels;
    /**
	 * @uml.property  name="_detectorType"
	 */
    private String _detectorType = "Gemini CCD UNKNOWN";

    public Detector(String directory, String prefix, String filename, String detectorType) throws Exception {
        super(directory + prefix + filename + Instrument.getSuffix());
        _detectorType = detectorType;
    }

    public int getDetectorPixels() {
        return _detectorPixels;
    }

    public void setDetectorPixels(int detectorPixels) {
        _detectorPixels = detectorPixels;
    }

    public String toString() {
        return "Detector : " + _detectorType;
    }
}
