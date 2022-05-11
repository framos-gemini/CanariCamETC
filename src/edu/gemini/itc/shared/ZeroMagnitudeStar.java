// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ZeroMagnitudeStar.java,v 1.3 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

/**
 * This class encapsulates the photon flux density (in photons/s/m^2/nm)
 * for a zero-magnitude star.
 */
public final class ZeroMagnitudeStar extends AverageFlux {
    // flux densities for each waveband for zero-magnitude star (of some type)
    // units are photons/s/m^2/nm
	public static int[] FLUX_DENSITY = {
		75484566, //"U_B", "B_B", "V_B", "R_B", "I_B",  Bessel System 
		137198996,
		98015142,
		68089950,
		45037869,
		66456005,	// "U_S", "G_S", "R_S", "I_S", "Z_S", Sloan System
		126833916,
		77155303,
		52358796,
		37804670,	
		18953844,	// "J_C", "H_C", "K_C",				// CTIO
		9665582,
		4774238,	
		19478925,	// "J_2", "H_2", "K_2",				// 2MASS
		9275054,
		4661080,	
		992877,		// "L'_S", "M'_S",					// Subaru/IRCS
		531084,		
		1176024,	// "I1_S", "I2_S", "I3_S","I4_S",		// Spitzer/IRAC
		599669,
		299282,
		118033,		
		1393229,	// "W1_W", "W2_W", "W3_W", "W4_W",	// WISE
		563306,
		41382,
		5739,		
		55764,	// "N_G", "Q_G"};						// Gemini/T-ReCS
		6269
	};

	

    // Keep anyone from instantiating this object.
    private ZeroMagnitudeStar() {
    }

    /**
     * Get average flux in photons/s/m^2/nm in specified waveband.
     * Overrides method in base class AverageFlux.
     */
    public static int getAverageFlux(String waveband) {
        return FLUX_DENSITY[WavebandDefinition.getWavebandIndex(waveband)];
    }
}
