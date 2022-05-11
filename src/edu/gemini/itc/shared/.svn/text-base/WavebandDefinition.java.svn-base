// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: WavebandDefinition.java,v 1.4 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

import java.util.HashMap;

/**
 * This class represents the definition of standard "wavebands",
 * U, B, V, R, I, J, H, K, L',M',N,Q
 * Standard units will be nm.
 * All classes should use this class for waveband information rather
 * than re-implementing the definitions and possibly causing an error.
 */
public final class WavebandDefinition {
    // The names of the standard wavebands in order of wavelength.
    //public static final String[] WAVEBAND_NAMES = {"U", "B", "V", "R", "I", "J", "H", "K", "L'", "M'", "N", "Q", "G'", "R'", "I'", "Z'"};
	public static final String[] WAVEBAND_NAMES = {	"U_B", "B_B", "V_B", "R_B", "I_B",  	// Bessel System 
													"U_S", "G_S", "R_S", "I_S", "Z_S",	// Sloan System
													"J_C", "H_C", "K_C",				// CTIO
													"J_2", "H_2", "K_2",				// 2MASS
													"L'_S", "M'_S",					// Subaru/IRCS
											      	"I1_S", "I2_S", "I3_S","I4_S",		// Spitzer/IRAC
											      	"W1_W", "W2_W", "W3_W", "W4_W",	// WISE
											      	"N_G", "Q_G"};						// Gemini/T-ReCS

    private static HashMap _bandNameToIndex;

    // static initializer
    static {
        // Fill table of indices into arrays
        _bandNameToIndex = new HashMap();
        for (int i = 0; i < WAVEBAND_NAMES.length; ++i) {
            _bandNameToIndex.put(WAVEBAND_NAMES[i], new Integer(i));
        }
    }

    // Waveband center in nm for      U_B,  B_S,     V_S,   R_S,   I_S,    J,    H,    K
    private static double[] _center = { 365.5, 440.0, 551.2, 658.6, 806.0,
    									356.2, 471.9, 618.5, 750.0, 896.1,
    									1246.6, 1631.2, 2142.6,			// CTIO
										1235.0, 1666.2, 2159.0,				// 2MASS
										3774.2, 4680.3,						// Subaru/IRCS 
										3557.3, 4504.9, 5738.6, 7927.4,		// Spitzer/IRAC
										3352.6, 4602.8, 11560.8, 22088.3,	// WISE
										10311.3, 21425.2};					// Gemini/T-ReCS  

    // Waveband width in nm
    private static double[] _width = {	114.3, 180.0, 214.2, 332.9, 212.5,
    									98.0, 176.6, 157.4, 140.0, 287.3,
    									197.2, 352.7, 353.8,				// CTIO
    									326.2, 344.4, 400.8,				// 2MASS
    									919.7, 378.6, 						// Subaru/IRCS
    									831.8, 1138.8, 1.6106, 3288.2,		// Spitzer/IRAC 
    									1118.3, 1378.1, 9818.3, 8390.6,		// WISE 
    									5947.9, 12667.0 };					// Gemini/T-ReCS
    /**
     *
     */
    public static int getWavebandIndex(String wavebandName) {
        wavebandName = wavebandName.toUpperCase();
        Integer index = (Integer) (_bandNameToIndex.get(wavebandName));
        if (index == null) return 0;
        return index.intValue();
    }

    
    public static double getCenter(String waveband) {
        return _center[getWavebandIndex(waveband)];
    }

    /**
     * Returns the width of specified waveband in nm.
     * If waveband does not exist, returns 0.
     */
    public static double getWidth(String waveband) {
        return _width[getWavebandIndex(waveband)];
    }

    /**
     * Returns the lower limit of specified waveband in nm.
     * If waveband does not exist, returns 0.
     */
    public static double getStart(String waveband) {
        return getCenter(waveband) - (getWidth(waveband) / 2);
    }

    /**
     * Returns the upper limit of specified waveband in nm.
     * If waveband does not exist, returns 0.
     */
    public static double getEnd(String waveband) {
        return getCenter(waveband) + (getWidth(waveband) / 2);
    }
}





