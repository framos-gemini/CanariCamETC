// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
//
package edu.gemini.itc.canaricam;

import edu.gemini.itc.parameters.ObservationDetailsParameters;
import edu.gemini.itc.shared.ITCConstants;
import edu.gemini.itc.shared.ITCParameters;
import edu.gemini.itc.shared.ITCMultiPartParser;
import edu.gemini.itc.shared.NoSuchParameterException;

import javax.servlet.http.HttpServletRequest;

/**
 * This class holds the information from the CanariCam section
 * of an ITC web page.  This object is constructed from a servlet request.
 */
public final class CanariCamParameters extends ITCParameters {
    // ITC web form parameter names.
    // These constants must be kept in sync with the web page form.
    // They are used to parse form data.
    public static final String INSTRUMENT_FILTER = "instrumentFilter";
    public static final String INSTRUMENT_WINDOW = "instrumentWindow";
    public static final String INSTRUMENT_GRATING = "instrumentDisperser";
    public static final String INSTRUMENT_CENTRAL_WAVELENGTH = "instrumentCentralWavelength";
    public static final String READ_NOISE = "readNoise";
    public static final String DARK_CURRENT ="darkCurrent";
    public static final String WELL_DEPTH = "wellDepth";
    public static final String FP_MASK = "instrumentFPMask";
    public static final String SPAT_BINNING = "spatBinning";
    public static final String SPEC_BINNING = "specBinning";
    
    // ITC web form input values.
    // These constants must be kept in sync with the web page form.
    // They are used to parse form data.
    //Windows
    public static final String KBR = "KBr";
    public static final String KRS5 = "KRS-5";
    public static final String ZNSE = "ZnSe";
    //Gratings
    public static final String LORES10_G5401 = "LoRes-10";
    public static final String LORES20_G5402 = "LoRes-20";
    public static final String HIRES10_G5403 = "HiRes-10";
    
    public static final int LORES10 = 0;
    public static final int LORES20 = 1;
    public static final int HIRES10 = 2;
    
    //Filters
    public static final String F_117 = "f117";
    public static final String F_ARIII = "ArIII";
    public static final String F_NEII = "NeII";
    public static final String F_NEIICONT = "NeIIcont";
    public static final String F_PAH113 = "PAH-11.3";
    public static final String F_PAH86 = "PAH-8.6";
    public static final String F_Q4 = "Q4";
    // Add because Canaricam.java analyze filter choose to assign option window. 
    public static final String F_Q1 = "Q1";
    public static final String F_Q8 = "Q8";
    public static final String F_Qb = "Qb";
    public static final String F_QH2 = "QH2";
    public static final String F_QSHORT = "Qshort";
    //public static final String F_QWIDE = "Q";
    public static final String F_QWIDE = "Qw";
    public static final String F_SIV = "SIV";
    public static final String F_Si1 = "Si-1";
    public static final String F_Si2 = "Si-2";
    public static final String F_Si3 = "Si-3";
    public static final String F_Si4 = "Si-4";
    public static final String F_Si5 = "Si-5";
    public static final String F_Si6 = "Si-6";
    public static final String F_K = "K";
    public static final String F_L = "L";
    public static final String F_M = "M";
    public static final String F_N = "N";
    public static final String F_SiC = "SiC";
    
    public static final String NO_DISPERSER = "none";
    public static final String LOW_READ_NOISE = "lowNoise";
    public static final String HIGH_READ_NOISE = "highNoise";
    public static final String HIGH_WELL_DEPTH = "highWell";
    public static final String LOW_WELL_DEPTH = "lowWell";
    public static final String SLIT0_17 = "slit0.17";
    public static final String SLIT0_20 = "slit0.20";
    public static final String SLIT0_23 = "slit0.23";
    public static final String SLIT0_26 = "slit0.26";
    public static final String SLIT0_36 = "slit0.36";
    public static final String SLIT0_41 = "slit0.41";
    public static final String SLIT0_45 = "slit0.45";
    public static final String SLIT0_52 = "slit0.52";
    public static final String SLIT1_04 = "slit1.04";
    public static final String IFU = "ifu";
    public static final String NO_SLIT = "none";
    
    // Data members
    /**
	 * @uml.property  name="_Filter"
	 */
    private String _Filter;  // filters
    /**
	 * @uml.property  name="_InstrumentWindow"
	 */
    private String _InstrumentWindow;
    /**
	 * @uml.property  name="_grating"
	 */
    private String _grating; // Grating or null
    /**
	 * @uml.property  name="_readNoise"
	 */
    private String _readNoise;
    /**
	 * @uml.property  name="_darkCurrent"
	 */
    private String _darkCurrent;
    /**
	 * @uml.property  name="_wellDepth"
	 */
    private String _wellDepth;
    /**
	 * @uml.property  name="_instrumentCentralWavelength"
	 */
    private String _instrumentCentralWavelength;
    /**
	 * @uml.property  name="_FP_Mask"
	 */
    private String _FP_Mask;
    /**
	 * @uml.property  name="_spatBinning"
	 */
    private String _spatBinning;
    /**
	 * @uml.property  name="_specBinning"
	 */
    private String _specBinning;
	private String _elfnParam;
    
    /**
     * Constructs a CanariCamParameters from a servlet request
     * @param r Servlet request containing the form data.
     * @throws Exception if input data is not parsable.
     */
    public CanariCamParameters(HttpServletRequest r) throws Exception {
        parseServletRequest(r);
    }

    /**
     *Constructs a CanariCamParameters from a MultipartParser
     * @param p MutipartParser that has all of the parameters and files Parsed
     *@throws Exception of cannot parse any of the parameters.
     */
    
    public CanariCamParameters(ITCMultiPartParser p) throws Exception {
        parseMultipartParameters(p);
    }
    
    public String getGratingByFilter() throws Exception {
    	
    	switch (_Filter) {
			case F_N: return "10.36";
			case F_Si1: return "7.8";
			case F_Si2: return "8.7";
			case F_Si3: return "9.8";
			case F_Si4: return "10.3";
			case F_Si5: return "11.6";
			case F_Si6: return "12.5";
			case F_SiC: return "11.75";
			case F_ARIII: return "8.99";
			case F_SIV: return "10.5";
			//case F_NEII: return "12.81";
			case F_NEII: return "12.8";
			case F_NEIICONT: return "13.1";
			case F_PAH113: return "11.3";
			case F_PAH86: return "8.6";
			case F_Q1: return "17.65";
			case F_Q4: return "20.5";
			case F_Q8: return "24.5";
			case F_QH2: return "17.0";
			//case F_QWIDE: return "20.9";
			case F_QWIDE: return "20.8";
			default :
				throw new Exception("Error (infer Gratting), Filter not analysed");
    	}
    }
    
    /** Parse parameters from a servlet request. */
    public void parseServletRequest(HttpServletRequest r) throws Exception {
        // Parse the acquisition camera section of the form.
        
        // Get filter
        _Filter = r.getParameter(INSTRUMENT_FILTER);
        if (_Filter == null) {
            ITCParameters.notFoundException(INSTRUMENT_FILTER);
        }
        
        // Get instrument Window
        _InstrumentWindow = r.getParameter(INSTRUMENT_WINDOW);
        if (_InstrumentWindow == null) {
            ITCParameters.notFoundException(INSTRUMENT_WINDOW);
        }
        
        // Get Grating
        _grating = r.getParameter(INSTRUMENT_GRATING);
        if (_grating == null) {
            ITCParameters.notFoundException(INSTRUMENT_GRATING);
        }
        
        _spatBinning = r.getParameter(SPAT_BINNING);
        if (_spatBinning == null) {
            ITCParameters.notFoundException(SPAT_BINNING);
        }
        
        _specBinning = r.getParameter(SPEC_BINNING);
        if (_specBinning == null) {
            ITCParameters.notFoundException(SPEC_BINNING);
        }
        
        // Get Instrument Central Wavelength
        _instrumentCentralWavelength = r.getParameter(INSTRUMENT_CENTRAL_WAVELENGTH);
        if (_instrumentCentralWavelength == null) {
            ITCParameters.notFoundException(
            INSTRUMENT_CENTRAL_WAVELENGTH);
        }
        if (_instrumentCentralWavelength.equals(" ")) {
            _instrumentCentralWavelength = "0";
        }
        
        _FP_Mask = r.getParameter(FP_MASK);
        if (_FP_Mask == null) {
            ITCParameters.notFoundException(FP_MASK);
        }
        
    }
    
    
    private String getWindow (String obsMode, String filter, String gratting) throws Exception {
    	System.out.println("CanaricamParameters " + filter + "   " + obsMode);
    	switch (obsMode) {
	    	case ObservationDetailsParameters.IMAGING:
	    		System.out.println("Imaging");
	    		switch (filter) {
	    			case F_N:
	    			case F_Si1:
	    			case F_Si2:
	    			case F_Si3:
	    			case F_Si4:
	    			case F_Si5:
	    			case F_Si6:
	    			case F_SiC:
	    			case F_ARIII:
	    			case F_SIV:
	    			case F_NEII:
	    			case F_NEIICONT:
	    			case F_PAH113:
	    			case F_PAH86:
	    				return ZNSE;
	    		
	    			case F_Q1:
	    			case F_Q4:
	    			case F_Q8:
	    			case F_QH2:
	    			case F_QWIDE:
	    				return KBR;
	    			case "none": 
		    			throw new Exception("Filter option not selected"+
		    	                "\nPlease select a grating ");
	    			default :
	    				System.out.println("Error (Imaging Filter), bad option in Filter.");
	    		}
	    		break;
	    	case ObservationDetailsParameters.POLARIMETRY:
	    		System.out.println("Polarimetry");
	    		return ZNSE;
	    	case ObservationDetailsParameters.SPECTROPOLARIMETRY:	
	    	case ObservationDetailsParameters.SPECTROSCOPY:
	    		System.out.println("Spectroscopy");
	    		switch (gratting) {
		    		case LORES10_G5401:
		    			return ZNSE;
		    		case LORES20_G5402:
		    			return KBR;
		    		case "none": 
		    			throw new Exception("Grating option not selected"+
		    	                "\nPlease select a grating.");
		    		default :
		    			System.out.println("Error (Spectroscopy Filter), bad option in Filter.");
	    		}
	    		break;
	    	default:
	    		System.out.println("Error, bad option.");
    	}
    	
    	return "";
    }
    
    public void parseMultipartParameters(ITCMultiPartParser p) throws Exception {
        // Parse GMOS details section of the form.
        try {
            _Filter = p.getParameter(INSTRUMENT_FILTER);
            _grating = p.getParameter(INSTRUMENT_GRATING);
            try {
            	_InstrumentWindow = p.getParameter(INSTRUMENT_WINDOW);
            } catch (Exception e) {
            	_InstrumentWindow = getWindow(p.getParameter(ObservationDetailsParameters.CALC_MODE),
            								  _Filter,
            								  _grating);
            }
            _spatBinning = p.getParameter(SPAT_BINNING);
            _specBinning = p.getParameter(SPEC_BINNING);
            _instrumentCentralWavelength = getGratingByFilter();
            if (_instrumentCentralWavelength.equals(" ")) {
                _instrumentCentralWavelength = "0";
            }
            _FP_Mask = p.getParameter(FP_MASK);    
            
        }
	catch (NoSuchParameterException e) {
            throw new Exception("The parameter "+ e.parameterName + " could not be found in the Telescope" +
            " Parameters Section of the form.  Either add this value or Contact the Helpdesk.");
        }
    }
    /**
     * Constructs a CanariCamParameters from a servlet request
     * @param r Servlet request containing the form data.
     * @throws Exception if input data is not parsable.
     */
    public CanariCamParameters(String Filter,
			       String instrumentWindow,
			       String grating,
			       String readNoise,
			       String wellDepth,
			       String darkCurrent,
			       String instrumentCentralWavelength,
			       String FP_Mask,
			       String spatBinning,
			       String specBinning) {
        _Filter = Filter;
        _InstrumentWindow = instrumentWindow;
        _grating = grating;
        _darkCurrent = darkCurrent;
        _readNoise = readNoise;
        _wellDepth = wellDepth;
        _instrumentCentralWavelength = instrumentCentralWavelength;
        _FP_Mask = FP_Mask;
        _spatBinning = spatBinning;
        _specBinning = specBinning;
        
    }
    
    public String getFilter() { return _Filter; }
    public String getInstrumentWindow() {return _InstrumentWindow;}
    public String getGrating() { return _grating; }
    public String getReadNoise() {return _readNoise;}
    public String getWellDepth() {return _wellDepth;}
    public String getDarkCurrent() {return _darkCurrent;}
    public String getFocalPlaneMask() {return _FP_Mask;}
    public double getInstrumentCentralWavelength(){
        return (new Double(_instrumentCentralWavelength).doubleValue())*1000; //Convert um to nm
    }
    public int getSpectralBinning() {
        return new Integer(_specBinning).intValue();
    }
    
    public int getSpatialBinning() {
        return new Integer(_spatBinning).intValue();
    }
    
    public double getFPMask() {
        if (_FP_Mask.equals(SLIT0_20)) return 0.20;
        else if (_FP_Mask.equals(SLIT0_17)) return 0.17;
        else if (_FP_Mask.equals(SLIT0_23)) return 0.23;
        else if (_FP_Mask.equals(SLIT0_26)) return 0.26;
        else if (_FP_Mask.equals(SLIT0_36)) return 0.36;
        else if (_FP_Mask.equals(SLIT0_41)) return 0.41;
        else if (_FP_Mask.equals(SLIT0_45)) return 0.45;
        else if (_FP_Mask.equals(SLIT0_52)) return 0.52;
        else if (_FP_Mask.equals(SLIT1_04)) return 1.04;
        else return -1.0;
    }
    public String getStringSlitWidth(){
        if (_FP_Mask.equals(SLIT0_20)) return "020";
        else if (_FP_Mask.equals(SLIT0_17)) return "017";
        else if (_FP_Mask.equals(SLIT0_23)) return "023";
        else if (_FP_Mask.equals(SLIT0_26)) return "026";
        else if (_FP_Mask.equals(SLIT0_36)) return "036";
        else if (_FP_Mask.equals(SLIT0_41)) return "041";
        else if (_FP_Mask.equals(SLIT0_45)) return "045";
        else if (_FP_Mask.equals(SLIT0_52)) return "052";
        else if (_FP_Mask.equals(SLIT1_04)) return "104";
        else return "none";
        
    }
        
    /** Return a human-readable string for debugging */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Filter:\t" + getFilter() + "\n");
        sb.append("Grating:\t" + getGrating() + "\n");
        sb.append("Instrument Central Wavelength:\t" +
        getInstrumentCentralWavelength() + "\n");
        sb.append("Focal Plane Mask: \t " + getFPMask()+ " arcsec slit \n");
        sb.append("\n");
        return sb.toString();
    }

	public int getElfn() {
		
		return Integer.parseInt(_elfnParam);
	}
}
