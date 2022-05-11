// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: AtmosphereVisitor.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.operation;

import edu.gemini.itc.shared.SampledSpectrumVisitor;
import edu.gemini.itc.shared.SampledSpectrum;
import edu.gemini.itc.shared.ArraySpectrum;
import edu.gemini.itc.shared.DefaultArraySpectrum;
import edu.gemini.itc.shared.ITCConstants;

/**
 * The AtmosphereVisitor class is designed to adjust the SED for the
 * Atmospheric Transmission at a given airmass.
 */
public class AtmosphereVisitor implements SampledSpectrumVisitor {
    /**
	 * @uml.property  name="fILENAME"
	 */
    private String FILENAME = null;

    private static double _airmass;
    /**
	 * @uml.property  name="_transmission"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private ArraySpectrum _transmission = null;

    /**
     * Constructs AtmosphereVisitor with specified air mass.
     * Airmass is a ratio of the length of line of sight through
     * atmosphere to height of atmosphere.  e.g looking straight up
     * airmass = 1.0.  Looking at an angle airmass > 1.
     * We will use a different convolution file for different
     * airmass ranges.
     */
    public AtmosphereVisitor(double airmass) throws Exception {
        _airmass = airmass;
        if (_airmass < 1.26) {
            FILENAME = "atmosphere_extinction_airmass10";
        } else if (_airmass > 1.75) {
            FILENAME = "atmosphere_extinction_airmass20";
        } else {
            FILENAME = "atmosphere_extinction_airmass15";
        }

        _transmission = new
                DefaultArraySpectrum(ITCConstants.TRANSMISSION_LIB + "/" +
                                     FILENAME + ITCConstants.DATA_SUFFIX);
    }

    /** @return the airmass used by this calculation */
    public double getAirMass() {
        return _airmass;
    }

    /**
     * Implements the SampledSpectrumVisitor interface
     */
    public void visit(SampledSpectrum sed) throws Exception {
        for (int i = 0; i < sed.getLength(); i++) {
            sed.setY(i, _transmission.getY(sed.getX(i)) * sed.getY(i));
        }
    }

    /**
     * This is the old implimentation of the SampledSpectrumVisitor interface
     * it was commented out Oct 4 1999 by Brian Walls
     *public void visit(SampledSpectrum sed) throws Exception
     *{
     *   for (int i=0; i < sed.getLength(); i++) {
     *  	  double power = -0.4*_transmission.getY(sed.getX(i))*getAirMass();
     *	  sed.setY(i, Math.pow(10, power)*sed.getY(i));
     *   }
     *}
     */

    public String toString() {
        return "AtmosphereVisitor using airmass " + _airmass;
    }
}
