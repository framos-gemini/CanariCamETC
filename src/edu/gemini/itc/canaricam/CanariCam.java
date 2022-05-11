
// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
//
package edu.gemini.itc.canaricam;

import edu.gemini.itc.shared.Instrument;
import edu.gemini.itc.shared.ITCConstants;
import edu.gemini.itc.shared.WavebandDefinition;
import edu.gemini.itc.shared.Filter;
import edu.gemini.itc.shared.Detector;
import edu.gemini.itc.shared.FixedOptics;
import edu.gemini.itc.shared.InstrumentWindow;
import edu.gemini.itc.shared.TextFileReader;
import edu.gemini.itc.operation.DetectorsTransmissionVisitor;
import edu.gemini.itc.parameters.ObservationDetailsParameters;
import edu.gemini.itc.parameters.SourceDefinitionParameters;
import edu.gemini.itc.parameters.TeleParameters;

/**
 * CanariCam specification class
 */
public class CanariCam extends Instrument {
    /** Related files will be in this subdir of lib */
    public static final String INSTR_DIR = "canaricam";
    
    /** Related files will start with this prefix */
    public static final String INSTR_PREFIX = "canaricam_";
    
    // Instrument reads its configuration from here.
    private static final String FILENAME = "canaricam" + getSuffix();
    
    private static final String ELFN_FILENAME = INSTR_PREFIX + "elfn" + getSuffix();
    
    private static final double WELL_DEPTH = 30000000.0;
    
    private static final int SAT_LIMIT_ADU = 65000;
    
    private static final double GAIN_DEEP_WELL = 568.5;
 
    private static final double GAIN_SHALLOW_WELL = 189.5;
        
    private static final double IMAGING_FRAME_TIME = .026;  //Seconds
    private static final double SPECTROSCOPY_LOW_RES_FRAME_TIME = .1; //Seconds
    private static final double SPECTROSCOPY_HI_RES_FRAME_TIME = .2; //Seconds
    
    private static final int DETECTOR_PIXELS = 320;
    
    private static edu.gemini.itc.operation.DetectorsTransmissionVisitor _dtv;    
    
    // Keep a reference to the color filter to ask for effective wavelength
    /**
	 * @uml.property  name="_Filter"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private Filter _Filter;
    /**
	 * @uml.property  name="_gratingOptics"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private GratingOptics _gratingOptics;
    /**
	 * @uml.property  name="_detector"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private Detector _detector;
    /**
	 * @uml.property  name="_sampling"
	 */
    private double _sampling;
    /**
	 * @uml.property  name="_filterUsed"
	 */
    private String _filterUsed;
    /**
	 * @uml.property  name="_instrumentWindow"
	 */
    private String _instrumentWindow;
    /**
	 * @uml.property  name="_grating"
	 */
    private String _grating;
    /**
	 * @uml.property  name="_readNoise"
	 */
    private double _readNoiseShallow;
    /**
	 * @uml.property  name="_wellDepth"
	 */
    private double _readNoiseDeep;
    /**
	 * @uml.property  name="_focalPlaneMask"
	 */
    private String _focalPlaneMask;
    /**
	 * @uml.property  name="_stringSlitWidth"
	 */
    private String _stringSlitWidth;
    /**
	 * @uml.property  name="_mode"
	 */
    private String _mode;
    /**
	 * @uml.property  name="_centralWavelength"
	 */
    private double _centralWavelength;
    /**
	 * @uml.property  name="_spectralBinning"
	 */
    private int _spectralBinning;
    /**
	 * @uml.property  name="_spatialBinning"
	 */
    private int _spatialBinning;
    
    /**
	 * @uml.property  name="elfn_param"
	 */
    private int elfn_param;  // extra low frequency noise
    
    // These are the limits of observable wavelength with this configuration.
    /**
	 * @uml.property  name="_observingStart"
	 */
    private double _observingStart;
    /**
	 * @uml.property  name="_observingEnd"
	 */
    private double _observingEnd;

	private double _gainDeep;

	private double _gainShallow;

	private double _saturation_detector;
    
    
    public CanariCam(CanariCamParameters tp,ObservationDetailsParameters odp) throws Exception {
        super(INSTR_DIR, FILENAME);
        // The instrument data file gives a start/end wavelength for
        // the instrument.  But with a filter in place, the filter
        // transmits wavelengths that are a subset of the original range.
        
        // The following datas are from canaricam.dat file. 
        
        
        
        _observingStart = super.getStart();  // Starting wavelength -> 1000
        _observingEnd = super.getEnd();		 // Ending wavelength  -> 28000
        _sampling = super.getSampling();	 // Sampling -> 2 (nm).
        _readNoiseShallow = super.getReadNoise();
        _readNoiseDeep = getTextFile().readDouble();
        _gainDeep = getTextFile().readDouble();
        _gainShallow = getTextFile().readDouble();
        _saturation_detector = getTextFile().readDouble();
        
        //setDarkCurrent(Double.parseDouble(tp.getDarkCurrent()));
        
        _focalPlaneMask = tp.getFocalPlaneMask();	// It is depend of Focal plane mask choose in html form. 
        
        _stringSlitWidth = tp.getStringSlitWidth(); // It is convertion of _focalPlaneMask. E.g: User choosen in form the
        											// option 0.17 arcsec slit, this value will be 0.17. 
        
        _grating = tp.getGrating();
        
        _filterUsed = tp.getFilter();
        
        _instrumentWindow = tp.getInstrumentWindow();
              
        _centralWavelength = tp.getInstrumentCentralWavelength();	// This value depend of choose filter
        															// Example, filter choose is "N" =centralWaveLength (in nm) = 10 * 1000
        
        _mode = odp.getCalculationMode();		// Observation mode. 
        _spectralBinning = tp.getSpectralBinning();	// Spectral binning is hidden in form, and it has a value equal 1.
        _spatialBinning = tp.getSpatialBinning();	// Spatial binning is hidden in form, and it has a value equal 1.
        
        //Read Extra-low freq data from file
        // Read file /canaricam/canaricam_elfn.dat, here describe shot noise or Photon Noise = sqrt(R∗ x t)
        String dir = ITCConstants.LIB + "/" + INSTR_DIR + "/";
        TextFileReader in = new TextFileReader(dir + ELFN_FILENAME);
        
        elfn_param = in.readInt();
        
        // elfn_param = tp.getElfn();
        //end of Extra-low freq data
        
        InstrumentWindow ccInstrumentWindow = new InstrumentWindow(getDirectory() +"/"+ getPrefix() 
        										+ _instrumentWindow + Instrument.getSuffix(), _instrumentWindow);
        
        addComponent( ccInstrumentWindow );
        
        
        /// !!!!!!!!NEED to Edit all of this !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Note for designers of other instruments:
        // Other instruments may not have filters and may just use
        // the range given in their instrument file.
        if (!(_filterUsed.equals("none"))) {
        	_Filter= new Filter(getPrefix(),_filterUsed,getDirectory()+"/",Filter.GET_EFFECTIVE_WAVELEN_FROM_FILE);
	        // Use the user define wavelength range in the filter curve
	        if (_Filter.getStart()>= _observingStart)
	        	_observingStart = _Filter.getStart();
	        if (_Filter.getEnd()<= _observingEnd)
	            _observingEnd = _Filter.getEnd();
	        
	        addComponent(_Filter);
        }
        
        
        FixedOptics _fixedOptics = new FixedOptics(getDirectory()+"/",getPrefix());
        addComponent(_fixedOptics);
        
        //Test to see that all conditions for Spectroscopy are met
        if (_mode.equals(ObservationDetailsParameters.SPECTROSCOPY) || 
        	_mode.equals(ObservationDetailsParameters.SPECTROPOLARIMETRY)){
            if (_grating.equals("none"))
                throw new Exception("Spectroscopy calculation method is selected but a grating"+
                " is not.\nPlease select a grating and a " +
                "focal plane mask in the Instrument " +
                "configuration section.");
            
            if (_focalPlaneMask.equals(CanariCamParameters.NO_SLIT)) {
            	String mode = (_mode.equals(ObservationDetailsParameters.SPECTROPOLARIMETRY)) ? "Spectropolarimetry" : "Spectroscopy";
                throw new Exception(mode + " calculation method is selected but a focal"+
                " plane mask is not.\nPlease select a "+
                "grating and a " +
                "focal plane mask in the Instrument " +
                "configuration section.");
            }
        }
        
        if (_mode.equals(ObservationDetailsParameters.IMAGING) || _mode.equals(ObservationDetailsParameters.POLARIMETRY)){
            if (_filterUsed.equals("none"))
                throw new Exception( _mode.toUpperCase() + "calculation method is selected but a filter"+
                " is not.\n  Please select a filter and resubmit the form to continue.");
            if (!_grating.equals("none"))
                throw new Exception( _mode.toUpperCase() + " calculation method is selected but a grating"+
                " is also selected.\nPlease deselect the grating or change the method to spectroscopy.");
            if (!_focalPlaneMask.equals("none"))
                throw new Exception( _mode.toUpperCase() + " calculation method is selected but a Focal"+
                " Plane Mask is also selected.\nPlease deselect the Focal Plane Mask"+
                " or change the method to spectroscopy.");
        }
        
        //Q1, Q4, QH2, Q8 o Qw
        if (_mode.equals(ObservationDetailsParameters.POLARIMETRY) 
        	&& (_filterUsed.equals(CanariCamParameters.F_Q1)
            	|| _filterUsed.equals(CanariCamParameters.F_Q4)
            	|| _filterUsed.equals(CanariCamParameters.F_Q8)
            	|| _filterUsed.equals(CanariCamParameters.F_QH2)
            	|| _filterUsed.equals(CanariCamParameters.F_QWIDE)
               )){
        	throw new Exception( _mode.toUpperCase() + " calculation method is selected but "+
        					"CanariCam polarimetry only available in the 10-micron window. \n"+
        					"Please select other filter and resubmit the form to continue.");
        }
        
        _detector = new Detector(getDirectory()+"/", getPrefix(), "det", "320x240 pixel Si-As IBC array");
        _detector.setDetectorPixels(DETECTOR_PIXELS);
        // Bad pixel = 0,  Good pixel = 1.
        _dtv = new edu.gemini.itc.operation.DetectorsTransmissionVisitor(_spectralBinning,
        											getDirectory()+"/"+ getPrefix()+ "ccdpix"+Instrument.getSuffix());
        
        if (!(_grating.equals("none"))) {
            // Read diffraction grating efficient.
            _gratingOptics = new GratingOptics(getDirectory()+"/",
            						_grating, 
            						_stringSlitWidth, 
            						_centralWavelength,
            						_detector.getDetectorPixels(),//_spectralBinning,
            						_spectralBinning);
            
            _sampling = _gratingOptics.getGratingDispersion_nmppix();
            _observingStart = _gratingOptics.getStart();
            _observingEnd = _gratingOptics.getEnd();
            
            if (!(_grating.equals("none"))&&!(_filterUsed.equals("none")))
                if ((_Filter.getStart() >= _gratingOptics.getEnd()) ||
                		(_Filter.getEnd() <= _gratingOptics.getStart())) {
                    
                	throw new Exception("The " + _filterUsed +" filter"+
                    " and the " +_grating +
                    " do not overlap with the requested wavelength.\n" +
                    " Please select a different filter, grating or wavelength.");
                }
            

            addComponent(_gratingOptics);
        }
        
        addComponent(_detector);
        
    }
    
    /**
     * Returns the effective observing wavelength.
     * This is properly calculated as a flux-weighted averate of
     * observed spectrum.  So this may be temporary.
     * @return Effective wavelength in nm
     */
    public int getEffectiveWavelength() {
        if (_grating.equals("none")) return (int)_Filter.getEffectiveWavelength();
        else return (int)_gratingOptics.getEffectiveWavelength();
        
    }
    
    public double getGratingResolution(){
        return _gratingOptics.getGratingResolution();
    }
    
    
    public String getGrating() {return _grating;}
    
    public double getGratingBlaze() {
        return _gratingOptics.getGratingBlaze();
    }
    
    public double getGratingDispersion_nm() {
        return _gratingOptics.getGratingDispersion_nm();
    }
    
    public double getGratingDispersion_nmppix() {
        return _gratingOptics.getGratingDispersion_nmppix();
    }
    
    
    /** Returns the subdirectory where this instrument's data files are. */
    //Changed Oct 19.  If any problem reading in lib files change back...
    public String getDirectory() { 
    	return ITCConstants.LIB + "/" + INSTR_DIR; 
    }
    
    public double getObservingStart() { return _observingStart; }
    public double getObservingEnd() { return _observingEnd; }
    public double getPixelSize() { return super.getPixelSize()*_spatialBinning; }
    public double getSpectralPixelWidth() {return _gratingOptics.getPixelWidth();}
    
    public double getSampling() { return _sampling;}
    
    public double getFrameTime() {
        if(_mode.equals(ObservationDetailsParameters.SPECTROSCOPY)){
            if(getGrating().equals(CanariCamParameters.HIRES10_G5403)){
                return SPECTROSCOPY_HI_RES_FRAME_TIME;
            } else {
                return SPECTROSCOPY_LOW_RES_FRAME_TIME;
            }
        } else {
            return IMAGING_FRAME_TIME;
        }
    }
    
    public double getFrameTimeDepend_F_G() throws Exception {
    	if (_grating != null || !_grating.equals("")) {
    		switch (_grating) {
    			case CanariCamParameters.LORES10_G5401: 
    			case CanariCamParameters.LORES20_G5402: return 0.051;
    		}
    	}
    	
        switch (_filterUsed) {
	        case CanariCamParameters.F_N:
	        case CanariCamParameters.F_QWIDE: 
	        case CanariCamParameters.F_SiC: return 0.017;
	        case CanariCamParameters.F_Si2: 
	        case CanariCamParameters.F_QH2: 
	        case CanariCamParameters.F_Si4: 
	        case CanariCamParameters.F_PAH113: return 0.025;
			case CanariCamParameters.F_Si1: 
			case CanariCamParameters.F_PAH86: 
			case CanariCamParameters.F_Q1: 
			case CanariCamParameters.F_Q4: 
			case CanariCamParameters.F_Q8: 
			case CanariCamParameters.F_Si3: 
			case CanariCamParameters.F_Si5: 
			case CanariCamParameters.F_Si6: 
			case CanariCamParameters.F_NEII:
			case CanariCamParameters.F_NEIICONT: return 0.034;
			case CanariCamParameters.F_ARIII: 
			case CanariCamParameters.F_SIV: return 0.051;
			
			default :
				throw new Exception("Error (infer Exposition Time), Filter not analysed");
        }
    }
    
    public int getSpectralBinning() {return _spectralBinning;}
    
    public int getSpatialBinning() {return _spatialBinning;}
    
    public int getExtraLowFreqNoise() {
        return elfn_param;
    }
    
    public int getElfn() {
    	return elfn_param;
    }
    
    /** The prefix on data file names for this instrument. */
    public static String getPrefix() { return INSTR_PREFIX; }
    
    public edu.gemini.itc.operation.DetectorsTransmissionVisitor getDetectorTransmision() {
        return _dtv;
    }
    
    public String getFocalPlaneCustom() {
    	switch(_focalPlaneMask) {
    		case ("none"): return "none";
    		case ("slit0.17"): return "0.17\" slit";
    		case ("slit0.20"): return "0.20\" slit";
    		case ("slit0.23"): return "0.23\" slit";
    		case ("slit0.26"): return "0.26\" slit";
    		case ("slit0.36"): return "0.36\" slit";
    		case ("slit0.41"): return "0.41\" slit";
    		case ("slit0.45"): return "0.45\" slit";
    		case ("slit0.52"): return "0.52\" slit";
    		case ("slit1.04"): return "1.04\" slit"; 
    	}
    	return _focalPlaneMask;
    }
    
    public String toString(){
        
        String s= "Instrument configuration: \n";
        s += super.opticalComponentsToString();
        if (!_focalPlaneMask.equals(CanariCamParameters.NO_SLIT))
            s += "<LI> Focal Plane Mask: " + getFocalPlaneCustom() +"\n";
        s += "\n";
        s += "Pixel Size in Spatial Direction: " + getPixelSize() +"\" \n";
        if (_mode.equals(ObservationDetailsParameters.SPECTROSCOPY))
            s += "Pixel Size in Spectral Direction: " + getGratingDispersion_nmppix()+"nm\n";
        return s;
    }

    /*
	 * @output: Return read noise depend of filter chosen by user
	 */
    
	public double getReadNoise() {
		
		if (_grating != null || !_grating.equals("")) {
    		switch (_grating) {
    			case CanariCamParameters.LORES10_G5401: return _readNoiseShallow;
    			case CanariCamParameters.LORES20_G5402: return _readNoiseShallow;
    		}
    	}
		
	   	switch (_Filter.getFilter()) {
	    	case CanariCamParameters.F_N: 
	    	case CanariCamParameters.F_Si1:
			case CanariCamParameters.F_Si3:
			case CanariCamParameters.F_Si5: 
			case CanariCamParameters.F_Si6: 
			case CanariCamParameters.F_SiC: 
			case CanariCamParameters.F_Q1: 
			case CanariCamParameters.F_Q4:
			case CanariCamParameters.F_QWIDE:
			case CanariCamParameters.F_Q8: return _readNoiseDeep;
			case CanariCamParameters.F_SIV:
			case CanariCamParameters.F_NEII: 
			case CanariCamParameters.F_NEIICONT: 
			case CanariCamParameters.F_PAH113: 
			case CanariCamParameters.F_PAH86: 
			case CanariCamParameters.F_Si2: 
			case CanariCamParameters.F_Si4: 
			case CanariCamParameters.F_ARIII: 
			case CanariCamParameters.F_QH2: return _readNoiseShallow;
			 
			default: 
			    return _readNoiseDeep;
    			//throw new Exception("Filter option not selected \n Please select a filter ");
	  	}
	}
	
	public double getSaturationLimit() throws Exception {
		if (_grating != null || !_grating.equals("")) {
    		switch (_grating) {
    			case CanariCamParameters.LORES10_G5401: 
    			//case CanariCamParameters.LORES20_G5402: return GAIN_SHALLOW_WELL * SAT_LIMIT_ADU;
    			case CanariCamParameters.LORES20_G5402: return _gainShallow * _saturation_detector;
    		}
    	}
		
		switch (_Filter.getFilter()) {
	    	case CanariCamParameters.F_N:  
	    	case CanariCamParameters.F_Si3:
	    	case CanariCamParameters.F_Si1:
	    	case CanariCamParameters.F_Si5:
			case CanariCamParameters.F_Si6:
			case CanariCamParameters.F_SiC:
			case CanariCamParameters.F_Q1: 
			case CanariCamParameters.F_Q4: 
			//case CanariCamParameters.F_Q8: return GAIN_DEEP_WELL * SAT_LIMIT_ADU;
			case CanariCamParameters.F_Q8: return _gainDeep * _saturation_detector;
			case CanariCamParameters.F_Si2:
			case CanariCamParameters.F_Si4:
			case CanariCamParameters.F_ARIII:
			case CanariCamParameters.F_SIV:
			case CanariCamParameters.F_NEII:
			case CanariCamParameters.F_NEIICONT: 
			case CanariCamParameters.F_PAH113: 
			case CanariCamParameters.F_PAH86: 
			//case CanariCamParameters.F_QH2: return GAIN_SHALLOW_WELL * SAT_LIMIT_ADU;
			case CanariCamParameters.F_QH2: return _gainShallow * _saturation_detector;
			case CanariCamParameters.F_QWIDE: 
		default: 
		    
			throw new Exception("Filter option not selected \n Please select a filter ");
		}
	}

	public void setDepthWell(double _readNoise) {
		this._readNoiseDeep = _readNoise;
	}

	public double getWellDepth() {
		return _readNoiseDeep;
	}

	public void setWellDepth(double wellDepth) {
		this._readNoiseDeep = wellDepth;
	}

	public double get_gainDeep() {
		return _gainDeep;
	}

	public void set_gainDeep(double _gainDeep) {
		this._gainDeep = _gainDeep;
	}

	public double get_gainShallow() {
		return _gainShallow;
	}

	public void set_gainShallow(double _gainShallow) {
		this._gainShallow = _gainShallow;
	}
	
	public String getFocalPlaneMask() {
		return _focalPlaneMask;
	}
}
