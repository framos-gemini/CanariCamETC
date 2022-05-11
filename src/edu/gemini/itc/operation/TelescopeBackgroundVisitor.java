// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: TelescopeBackgroundVisitor.java,v 1.5 2004/01/12 16:53:55 bwalls Exp $
//
package edu.gemini.itc.operation;

import edu.gemini.itc.shared.SampledSpectrumVisitor;
import edu.gemini.itc.shared.SampledSpectrum;
import edu.gemini.itc.shared.ArraySpectrum;
import edu.gemini.itc.shared.DefaultArraySpectrum;
import edu.gemini.itc.shared.ITCConstants;

/**
 * The TelescopeBackgroundVisitor class is designed to adjust the SED for the
 * background given off by the telescope.
 */
public class TelescopeBackgroundVisitor implements SampledSpectrumVisitor {
    /**
	 * @uml.property  name="_setup"
	 */
    private String _setup;
    /**
	 * @uml.property  name="_filename_base"
	 */
    private String _filename_base;
    
    /**
	 * @uml.property  name="_telescopeBack"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private ArraySpectrum _telescopeBack = null;
    
    /**
     * Constructs TelescopeBackgroundVisitor with specified port and coating.
     * We will use a different background file for different
     * ports and coatings.
     */
    public TelescopeBackgroundVisitor(String coating, String port, String site, String wavelenRange) throws Exception {
        
        this(coating, port, "" , site, wavelenRange);
    }
    
    public TelescopeBackgroundVisitor(String coating, String port, String filename_base, String site, String wavelenRange) throws Exception {
         _telescopeBack = new DefaultArraySpectrum("/HI-Res/cp7-26/tele_emiss/telEmiss_3al.dat");
        
    }
    
    /** @return the airmass used by this calculation */
    public String getSetup() {return _setup;}
    
    /**
     * Implements the SampledSpectrumVisitor interface
     */
    public void visit(SampledSpectrum sed) throws Exception {
        for (int i=0; i < sed.getLength(); i++) {
            sed.setY(i, _telescopeBack.getY(sed.getX(i))+sed.getY(i));
        }
    }
    
    
    public String toString() {
        return "TelescopeBackgroundVisitor using setup " + _setup;
    }
}
