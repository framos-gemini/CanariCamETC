// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: Waveband.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

/**
 * This class represents a standard "waveband", one of
 * U, B, V, R, I, J, H, K
 * Standard units will be nm.
 * This class does not enforce that the band name be a valid one.
 */
public final class Waveband {
    /**
	 * @uml.property  name="_bandName"
	 */
    private String _bandName;

    public Waveband(String bandName) {
        _bandName = bandName;
    }

    public double getCenter() {
        return WavebandDefinition.getCenter(_bandName);
    }

    /**
     * Returns the width of specified waveband in nm.
     * If waveband does not exist, returns 0.
     */
    public double getWidth(String waveband) {
        return WavebandDefinition.getWidth(_bandName);
    }

    /**
     * Returns the lower limit of specified waveband in nm.
     * If waveband does not exist, returns 0.
     */
    public double getStart(String waveband) {
        return WavebandDefinition.getStart(_bandName);
    }

    /**
     * Returns the upper limit of specified waveband in nm.
     * If waveband does not exist, returns 0.
     */
    public double getEnd(String waveband) {
        return WavebandDefinition.getEnd(_bandName);
    }
}
