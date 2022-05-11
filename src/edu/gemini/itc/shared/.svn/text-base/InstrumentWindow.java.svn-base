// Copyright 2001 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
package edu.gemini.itc.shared;


import edu.gemini.itc.shared.TransmissionElement;

/**
 *  This is just a basic transmission element for user selectable windows
 *  on instruments.
 */

public class InstrumentWindow extends TransmissionElement {
    /**
	 * @uml.property  name="windowName"
	 */
    String windowName;

    public InstrumentWindow(String resource, String windowName) throws Exception {
        super(resource);
        this.windowName = windowName;

    }

    public String toString() {
        return "User Selectable Window: " + windowName;
    }

}
