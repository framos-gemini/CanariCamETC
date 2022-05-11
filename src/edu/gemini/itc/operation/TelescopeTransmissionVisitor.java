// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: TelescopeTransmissionVisitor.java,v 1.3 2004/01/12 16:53:55 bwalls Exp $
//
package edu.gemini.itc.operation;

import edu.gemini.itc.shared.SampledSpectrumVisitor;
import edu.gemini.itc.shared.SampledSpectrum;
import edu.gemini.itc.shared.ArraySpectrum;
import edu.gemini.itc.shared.DefaultArraySpectrum;
import edu.gemini.itc.shared.TransmissionElement;
import edu.gemini.itc.shared.ITCConstants;

/**
 * The TelescopeTransmissionVisitor is designed to adjust the SED for the
 * Telesope Tranmsission.
 */
public final class TelescopeTransmissionVisitor extends TransmissionElement {
    // These constants define the different mirror surfaces used
    public static final String ALUMINUM = "aluminium";
    public static final String SILVER    = "silver";
    public static final String UP = "up";
    public static final String UP_GS = "upGS";
    public static final String SIDE = "side";
    public static final String SIDE_GS = "sideGS";
    
    private static final String _COATING = "_coating_";
    
    /**
     * The TelTrans constructor takes two arguments: one detailing what
     * type of coating is used, and the other detailing how many mirrors
     * should be used.
     */
    public TelescopeTransmissionVisitor(String coating, String issPort)
    throws Exception {
    	String fileName = "al"+ _COATING +"up";
        setTransmissionSpectrum(ITCConstants.TRANSMISSION_LIB + "/" + fileName + ITCConstants.DATA_SUFFIX);
    }
    
    public String toString() {
        return ("TelescopeTransmission");
    }
}
