// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: WaterTransmissionVisitor.java,v 1.4 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.operation;

import edu.gemini.itc.shared.SampledSpectrumVisitor;
import edu.gemini.itc.shared.SampledSpectrum;
import edu.gemini.itc.shared.ArraySpectrum;
import edu.gemini.itc.shared.DefaultArraySpectrum;
import edu.gemini.itc.shared.TransmissionElement;
import edu.gemini.itc.shared.ITCConstants;

/**
 * The WaterTransmissionVisitor is designed to adjust the SED for
 * water in the atmosphere.
 */
public final class WaterTransmissionVisitor extends TransmissionElement {
    private static final String FILENAME = "water_trans";

    /**
     * Constructs transmission visitor for water vapor.
     */
    public WaterTransmissionVisitor(int skyTransparencyWater)
            throws Exception {
        setTransmissionSpectrum(ITCConstants.TRANSMISSION_LIB + "/" + FILENAME +
                                skyTransparencyWater + ITCConstants.DATA_SUFFIX);
    }

    public WaterTransmissionVisitor(int skyTransparencyWater, double airMass,
                                    String file_name, String site, String wavelenRange)
            throws Exception {
         System.out.println ("SkyValue:" + skyTransparencyWater +"Airmass:" + airMass);
         String _airmassCategory;
         String _transmissionCategory;
         
         if (airMass <= 1.0)
             _airmassCategory="10";
         else if (airMass <= 1.1)
             _airmassCategory="11";
         else if (airMass <= 1.5)
             _airmassCategory="15";
         else
            _airmassCategory="20";
            
        if (skyTransparencyWater == 1) 
            _transmissionCategory="05p0_";
        else if (skyTransparencyWater == 2)
            _transmissionCategory="07p0_";
        else if (skyTransparencyWater == 3 || skyTransparencyWater == 4)  ////?????? 2004Nov22 Why 3 and 4?
            _transmissionCategory="10p0_";
        else 
        	_transmissionCategory="20p0_";
        
        setTransmissionSpectrum("/HI-Res/" + site + wavelenRange + ITCConstants.TRANSMISSION_LIB + "/"
               +file_name + _transmissionCategory + _airmassCategory + ITCConstants.DATA_SUFFIX);
            
    }

    public String toString() {
        return ("WaterTransmission");
    }
}
