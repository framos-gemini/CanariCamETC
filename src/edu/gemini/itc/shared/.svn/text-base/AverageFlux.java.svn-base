// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: AverageFlux.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

/**
 * This base class is used in the normalization process where the client
 * wants to know the average flux of an object in a certain waveband.
 * To keep things consistent all clients and implementers should use
 * units of photons/s/m^2/nm
 */
// Programmer's note: interfaces can't have static methods, so this
// had to be a class.  Static methods can't be abstract, so the method
// had to have a default implementation.
// Subclasses should override getAverageFlux().

public abstract class AverageFlux {
    /**
     * Get average flux in photons/s/m^2/nm in specified waveband.
     */
    public static int getAverageFlux(String waveband) {
        return 0;
    }
}
