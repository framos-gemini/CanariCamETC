// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ObservationDetailsParameters.java,v 1.9 2007/11/14 14:31:02 varosi Exp $
//
package edu.gemini.itc.parameters;

import javax.servlet.http.HttpServletRequest;

import edu.gemini.itc.shared.ITCParameters;
import edu.gemini.itc.shared.ITCMultiPartParser;
import edu.gemini.itc.shared.NoSuchParameterException;
import edu.gemini.itc.shared.FormatStringWriter;
/**
 * This class holds the information from the Observation Details section
 * of an ITC web page.  This object is constructed from a servlet request.
 */
public final class ObservationDetailsParameters extends ITCParameters {
    // ITC web form parameter names.
    // These constants must be kept in sync with the web page form.
    // They are used to parse form data.
    public static final String CALC_METHOD = "calcMethod";
    public static final String CALC_MODE = "calcMode";
    //define the parameters for imaging/polarimetry methods A, B and C.
    public static final String NUM_EXPOSURES = "numExpA";
    public static final String NUM_EXPOSURES_B = "numExpB";
    public static final String EXP_TIME = "expTimeA";
    public static final String EXP_TIME_B = "expTimeB";
    public static final String SRC_FRACTION = "fracOnSourceA";
    public static final String SRC_FRACTION_B = "fracOnSourceB";
    public static final String SRC_FRACTION_C = "fracOnSourceC";
    public static final String SRC_FRACTION_D = "fracOnSourceD";
    public static final String TOTAL_OBS_TIME = "totTimeA";
    public static final String TOTAL_OBS_TIME_D = "totTimeD";
    public static final String SIGMA = "sigmaB";
    public static final String POLINERR = "polinErrC";
    //define the parameters for spectroscopy method A
    public static final String NUM_EXPOSURES_SP = "numExpSpA";
    public static final String EXP_TIME_SP = "expTimeSpA";
    public static final String SRC_FRACTION_SP = "fracOnSourceSpA";

    public static final String ANAL_METHOD = "analMethod";
    public static final String APER_TYPE = "aperType";
    public static final String APER_TYPE_SP = "aperTypeSp";
    public static final String APER_DIAM = "userAperDiam";
    public static final String APER_DIAM_SP = "userAperDiamSp";
    public static final String AUTO_SKY_APER = "autoSkyAper";
    public static final String USER_SKY_APER = "userSkyAper";

    // ITC web form input values.
    // These constants must be kept in sync with the web page form.
    // They are used to parse form data.
    public static final String S2N = "s2n";
    public static final String POLERR = "polarErr";
    public static final String INTTIME = "intTime";
    public static final String INTTIMEPOL = "intTimePolar";
    public static final String IMAGING = "imaging";
    public static final String SPECTROSCOPY = "spectroscopy";
    public static final String SPECTROPOLARIMETRY = "spectroPolarimetry";
    public static final String POLARIMETRY = "polarimetry";
    public static final String AUTO_APER = "autoAper";
    public static final String USER_APER = "userAper";

    // Data members
    /**
	 * @uml.property  name="_calcMode"
	 */
    private String _calcMode; //imgaging or spectroscopy
    /**
	 * @uml.property  name="_calcMethod"
	 */
    private String _calcMethod;  // S/N given time or time given S/N
    
    private String _calcMethodRaw;
    /**
	 * @uml.property  name="_numExposures"
	 */
    private int _numExposures;
    /**
	 * @uml.property  name="_exposureTime"
	 */
    private double _exposureTime;   // in seconds
    /**
	 * @uml.property  name="_totalObservationTime"
	 */
    private double _totalObservationTime;  // Total observation Time, some instruments use this.
    /**
	 * @uml.property  name="_sourceFraction"
	 */
    private double _sourceFraction;  // fraction of exposures containing source
    /**
	 * @uml.property  name="_snRatio"
	 */
    private double _snRatio;  // ratio desired
    /**
	 * @uml.property  name="_polarizError"
	 */
    private double _polarizError;   // % polarization error.

    /**
	 * @uml.property  name="_analysisMethod"
	 */
    private String _analysisMethod; // imaging or spectroscopy
    /**
	 * @uml.property  name="_apertureType"
	 */
    private String _apertureType; // auto or user
    /**
	 * @uml.property  name="_apertureDiameter"
	 */
    private double _apertureDiameter; // in arcsec
    /**
	 * @uml.property  name="_skyApertureDiameter"
	 */
    private double _skyApertureDiameter;
//------------------------------------------------------------------------------------------------------
    /**
     *Constructs a ObservationDetailsParameters from a MultipartParser
     * @param p MutipartParser that has all of the parameters and files Parsed
     *@throws Exception of cannot parse any of the parameters.
     */

    public ObservationDetailsParameters(ITCMultiPartParser p) throws Exception {
        parseMultipartParameters(p);
    }
//------------------------------------------------------------------------------------------------------
    /** Note:This method is deprecated (obsolete).
     * Constructs a ObservationDetailsParameters from a servlet request
     * @param r Servlet request containing the form data.
     * @throws Exception if input data is not parsable.
     */
    public ObservationDetailsParameters(HttpServletRequest r) throws Exception {
        parseServletRequest(r);
    }
//------------------------------------------------------------------------------------------------------

    public ObservationDetailsParameters(String calcMode,
                                        String calcMethod,
                                        int numExposures,
                                        double exposureTime,
                                        double sourceFraction,
                                        double snRatio,
                                        String analysisMethod,
                                        String apertureType,
                                        double apertureDiameter,
                                        double skyApertureDiameter) {
        _calcMode = calcMode;
        _calcMethod = calcMethod;
        _numExposures = numExposures;
        _exposureTime = exposureTime;
        _sourceFraction = sourceFraction;
        _snRatio = snRatio;
        _analysisMethod = analysisMethod;
        _apertureType = apertureType;
        _apertureDiameter = apertureDiameter;
        _skyApertureDiameter = skyApertureDiameter;
    }
//------------------------------------------------------------------------------------------------------

    public void parseMultipartParameters(ITCMultiPartParser p) throws Exception {
        // Parse Telescope details section of the form.
        try {
        	_calcMode = p.getParameter(CALC_MODE);
            _calcMethod = p.getParameter(CALC_METHOD);
            _calcMethodRaw = _calcMethod;
            if (_calcMethod.equals(S2N)) {
                if (p.parameterExists(TOTAL_OBS_TIME)) {
                	_totalObservationTime = ITCParameters.parseInt( p.getParameter(TOTAL_OBS_TIME),	"Total Observation Time");
                } else {
                    p.getParameter(TOTAL_OBS_TIME);  // DUMMY Parameter to throw an exception.
                }
                _sourceFraction = ITCParameters.parseDouble(p.getParameter(SRC_FRACTION), "Exposures Containing Source");
                if (_sourceFraction < 0) 
                	_sourceFraction *= -1;
            }
            else if (_calcMethod.equals(INTTIME)) {
				if (_calcMode.equals(SPECTROSCOPY)) {
				    throw new Exception("Total integration time to achieve a specific S/N ratio is not supported in spectroscopy mode.\n Please select the Total S/N method.");
				}
		
				if (p.parameterExists(EXP_TIME_B))
				    _exposureTime = ITCParameters.parseDouble( p.getParameter(EXP_TIME_B), "Exposure Time");
				else
				    _totalObservationTime = 1;  //_totalObservationTime not used for int time just set it to 1.
		
				_snRatio = ITCParameters.parseDouble( p.getParameter(SIGMA), "Sigma");
				_sourceFraction = ITCParameters.parseDouble( p.getParameter(SRC_FRACTION_B),
									     "Fraction of Exposures Containing Source");
				if (_calcMode.equals(POLARIMETRY)) {
				    //_polarizError = 0.5 / Math.sqrt( _snRatio / 282 );
					_polarizError = 100 * Math.sqrt(2)/_snRatio;
				}
	
				if (_exposureTime < 0) 
					_exposureTime *= -1;
		        if (_snRatio < 0) 
		        	_snRatio *= -1;
		        if (_sourceFraction < 0) 
		        	_sourceFraction *= -1;
	        }
		    else if (_calcMethod.equals(INTTIMEPOL)) {
				if (p.parameterExists(EXP_TIME_B))
				    _exposureTime = ITCParameters.parseDouble( p.getParameter(EXP_TIME_B), "Exposure Time");
				else
				    _totalObservationTime = 1;  //_totalObservationTime not used for int time just set it to 1.
		
				if (_calcMode.equals(POLARIMETRY)) {
				    _sourceFraction = ITCParameters.parseDouble( p.getParameter(SRC_FRACTION_C), "Exposures Containing Source");
		            if( p.parameterExists(POLINERR) ) {
		            	_polarizError = ITCParameters.parseDouble( p.getParameter(POLINERR), "Polarization Error");
		            	if (_polarizError < 0) 
		            		_polarizError *= -1;
						//double peRatio = 0.5/_polarizError;
						//_snRatio = 282 * peRatio * peRatio;
		            	_snRatio = 100 * Math.sqrt(2)/_polarizError;
				    }
				    else throw new Exception("Missing Polarimetry Error %");
				}
				else 
					throw new Exception("Time to achieve a specific Polarization Error is only for Polarimetry mode.\nPlease select different method or Polarimetry mode.");
	
				if (_exposureTime < 0) 
					_exposureTime *= -1;
		        if (_snRatio < 0) 
		        	_snRatio *= -1;
		        if (_sourceFraction < 0) 
		        	_sourceFraction *= -1;
		    }
		    else if (_calcMethod.equals(POLERR)) {
				if (_calcMode.equals(POLARIMETRY)) {
				    if (p.parameterExists(TOTAL_OBS_TIME_D)) {
					_totalObservationTime = ITCParameters.parseInt( p.getParameter(TOTAL_OBS_TIME_D),
											"Total Observation Time");
				    }
				    else p.getParameter(TOTAL_OBS_TIME_D);  // DUMMY Parameter to throw an exception.
		
				    //do usual SNR calc. and then convert SNR to Pol.Err. in CanariCamRecipe.java:
				    _calcMethod = S2N;
				    _sourceFraction = ITCParameters.parseDouble(p.getParameter(SRC_FRACTION_D), "Exposures Containing Source");
				    if (_sourceFraction < 0) _sourceFraction *= -1;
				}
				else if (_calcMode.equals(SPECTROPOLARIMETRY)) {
					_calcMethod = S2N;
					_totalObservationTime = ITCParameters.parseInt( p.getParameter(TOTAL_OBS_TIME_D),	"Total Observation Time");
	                _sourceFraction = ITCParameters.parseDouble(p.getParameter(SRC_FRACTION_D), "Exposures Containing Source");
			    	if (_sourceFraction < 0) 
			    		_sourceFraction *= -1;
				}
				else throw new Exception("Polarization Error calculation is only for Polarimetry mode.\nPlease select different method or Polarimetry mode.");
		    }
		    else throw new Exception("Unrecognized calculation mode: " + getCalculationMode());
	
	        // Aperture Section
	        String skyAper;
	        _apertureType = p.getParameter(APER_TYPE);
	        if (_apertureType.equals(AUTO_APER)) {
	            skyAper = p.getParameter(AUTO_SKY_APER);
	        }
		    else if (_apertureType.equals(USER_APER)) {
	                skyAper = p.getParameter(USER_SKY_APER);
	                _apertureDiameter = ITCParameters.parseDouble(p.getParameter(APER_DIAM), "Aperture Diameter");
	                if (_apertureDiameter < 0) _apertureDiameter *= -1;
	        }
		    else {
		    	throw new Exception("Unrecognized Aperture type: " + _apertureType + ". Contact Helpdesk.");
	        }
	        _skyApertureDiameter = ITCParameters.parseDouble(skyAper, "Sky Aperture Diameter");
	        if (_skyApertureDiameter < 0) _skyApertureDiameter *= -1;
	        if (_skyApertureDiameter < 1)
	        	throw new Exception("The Sky aperture: " + _skyApertureDiameter + " must be 1 or greater.  Please retype the value and resubmit.");
	    }
		catch (NoSuchParameterException e) {
	            throw new Exception("The parameter " + e.parameterName + " could not be found in the Observation" +
	                                " Details Section of the form. \nEither add this value or Contact the Helpdesk.");
	    }
    }
    //end of parseMultipartParameters
//------------------------------------------------------------------------------------------------------

    public String getCalculationMethod() {	        return _calcMethod;    }
    
    public String getCalcMethodRaw() { return _calcMethodRaw; }  

    public String getCalculationMode() {        return _calcMode;    }

    public int getNumExposures() {        return _numExposures;    }

    public void setNumExposures(int numExposures) {        _numExposures = numExposures;    }

    public double getExposureTime() {        return _exposureTime;    }

    public void setExposureTime(double exposureTime) {
    	_exposureTime = exposureTime;    
    }

    public double getTotalObservationTime() {  return _totalObservationTime;    }

    public double getSourceFraction() {        return _sourceFraction;    }

    public double getSNRatio() {      return _snRatio;    }

    public double getPolarizError() { return _polarizError; }

    public String getAnalysisMethod() {        return _analysisMethod;    }

    public String getApertureType() {        return _apertureType;    }

    public double getApertureDiameter() {        return _apertureDiameter;    }

    public double getSkyApertureDiameter() {        return _skyApertureDiameter;    }

    /** Return a human-readable string for debugging */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Calculation Method:\t" + getCalculationMethod() + "\n");
        sb.append("Num Exposures:\t\t" + getNumExposures() + "\n");
        sb.append("Exposure Time:\t\t" + getExposureTime() + "\n");
        sb.append("Fraction on Source:\t" + getSourceFraction() + "\n");
        sb.append("SN Ratio:\t\t" + getSNRatio() + "\n");
        sb.append("Analysis Method:\t" + getAnalysisMethod() + "\n");
        sb.append("Aperture Type:\t\t" + getApertureType() + "\n");
        sb.append("Aperture Diameter:\t" + getApertureDiameter() + "\n");
        sb.append("\n");
        return sb.toString();
    }
//------------------------------------------------------------------------------------------------------
    //used for output at bottom of results webpage:

    public String printParameterSummary() {
        StringBuffer sb = new StringBuffer();
        // This object is used to format numerical strings.
        FormatStringWriter device = new FormatStringWriter();
        device.setPrecision(3);  // Two decimal places
        device.clear();
        sb.append("Calculation and analysis methods:\n");
        sb.append("<UL> <LI>mode:  " + getCalculationMode() + "\n");
        sb.append("<LI>Calculation of ");

        if (getCalculationMethod().equals(S2N)) {
            sb.append("S/N ratio with " + getNumExposures() + " exposures of " + device.toString(getExposureTime()) + " seconds on source.\n");
        } else {
            sb.append("integration time from a S/N ratio of " + device.toString(getSNRatio()) + " for exposures of");
            sb.append(" " + device.toString(getExposureTime()) + " on source.\n");
        }
        device.setPrecision(1); 
        sb.append("<LI>Analysis performed for aperture ");

        if (getApertureType().equals(AUTO_APER)) {
            sb.append("that gives 'optimum' S/N ");
        } else {
            sb.append("of diameter " + device.toString(getApertureDiameter()) + "\" ");
        }
        
        device.setPrecision(0);  // Two decimal places
        sb.append("and a sky aperture that is " + device.toString(getSkyApertureDiameter()) + " times the target apeture.\n </LI> </UL>");

        return sb.toString();
    }
    
//------------------------------------------------------------------------------------------------------
    //NOTE: parseServletRequest is deprecated:  use parseMultipartParameters above.

    /** Parse parameters from a servlet request. */
    public void parseServletRequest(HttpServletRequest r) throws Exception {
        // Parse the observation details section of the form.
        _calcMode = r.getParameter(CALC_MODE);
        if (_calcMode == null) {
            ITCParameters.notFoundException(CALC_MODE);
        }

        if (_calcMode.equals(IMAGING)) {
            // Get Calculation method - S/N given time  or  time given S/N
            _calcMethod = r.getParameter(CALC_METHOD);

            if (_calcMethod == null) {
                ITCParameters.notFoundException(CALC_METHOD);
            }
            if (_calcMethod.equals(S2N)) {
                String s = r.getParameter(NUM_EXPOSURES);
                if (s == null) {
                    ITCParameters.notFoundException(NUM_EXPOSURES);
                }
                _numExposures = ITCParameters.parseInt(s, "Number of exposures");
                if (_numExposures < 0) _numExposures *= -1;
                s = r.getParameter(EXP_TIME);
                if (s == null) {
                    ITCParameters.notFoundException(EXP_TIME);
                }
                _exposureTime = ITCParameters.parseDouble(s, "Exposure time");
                if (_exposureTime < 0) _exposureTime *= -1;
                s = r.getParameter(SRC_FRACTION);
                if (s == null) {
                    ITCParameters.notFoundException(SRC_FRACTION);
                }
                _sourceFraction =
                        ITCParameters.parseDouble(s, "Exposures containing source");
                if (_sourceFraction < 0) _sourceFraction *= -1;
            }
	    else if (_calcMethod.equals(INTTIME)) {

                String s = r.getParameter(EXP_TIME_B);
                if (s == null) {
                    ITCParameters.notFoundException(EXP_TIME_B);
                }
                _exposureTime = ITCParameters.parseDouble(s, "Exposure time");
                if (_exposureTime < 0) _exposureTime *= -1;
                s = r.getParameter(SIGMA);
                if (s == null) {
                    ITCParameters.notFoundException(SIGMA);
                }
                _snRatio = ITCParameters.parseDouble(s, "Sigma");
                if (_snRatio < 0) _snRatio *= -1;
                s = r.getParameter(SRC_FRACTION_B);
                if (s == null) {
                    ITCParameters.notFoundException(SRC_FRACTION_B);
                }
                _sourceFraction =
                        ITCParameters.parseDouble(s, "Exposures containing source");
                if (_sourceFraction < 0) _sourceFraction *= -1;
            } else {
                throw new Exception("Unrecognized calculation method: " +
                                    getCalculationMethod());
            }
        } else if (_calcMode.equals(SPECTROSCOPY)) {

            _calcMethod = r.getParameter(CALC_METHOD);

            if (_calcMethod == null) {
                ITCParameters.notFoundException(CALC_METHOD);
            }
            if (_calcMethod.equals(S2N)) {
                String s = r.getParameter(NUM_EXPOSURES);
                if (s == null) {
                    ITCParameters.notFoundException(NUM_EXPOSURES);
                }
                _numExposures = ITCParameters.parseInt(s, "Number of exposures");
                if (_numExposures < 0) _numExposures *= -1;
                s = r.getParameter(EXP_TIME);
                if (s == null) {
                    ITCParameters.notFoundException(EXP_TIME);
                }
                _exposureTime = ITCParameters.parseDouble(s, "Exposure time");
                if (_exposureTime < 0) _exposureTime *= -1;
                s = r.getParameter(SRC_FRACTION);
                if (s == null) {
                    ITCParameters.notFoundException(SRC_FRACTION);
                }
                _sourceFraction =
                        ITCParameters.parseDouble(s, "Exposures containing source");
                if (_sourceFraction < 0) _sourceFraction *= -1;

            } else {
                throw new Exception("Total integration time to achieve a specific \n" +
                                    "S/N ratio is not supported in spectroscopy mode.  \nPlease select the Total S/N method. ");

            }
        } else {
            throw new Exception("Unrecognized calculation mode: " +
                                getCalculationMode());
        }

        String skyAper;
        _apertureType = r.getParameter(APER_TYPE);
        if (_apertureType == null) {
            ITCParameters.notFoundException(APER_TYPE);
        }
        if (_apertureType.equals(AUTO_APER)) {
            // nothing to do here
            skyAper = r.getParameter(AUTO_SKY_APER);
        } else if (_apertureType.equals(USER_APER)) {
            skyAper = r.getParameter(USER_SKY_APER);
            String aperDiam = r.getParameter(APER_DIAM);
            if (aperDiam == null) {
                ITCParameters.notFoundException(APER_DIAM);
            }
            _apertureDiameter =
                    ITCParameters.parseDouble(aperDiam, "Aperture diameter");
            if (_apertureDiameter < 0) _apertureDiameter *= -1;
        } else {
            throw new Exception("Unrecognized aperture type: " +
                                _apertureType);
        }
        _skyApertureDiameter = ITCParameters.parseDouble(skyAper, "Sky Aperture Diameter");
        if (_skyApertureDiameter < 1)
            throw new Exception("The Sky aperture: " + _skyApertureDiameter +
                                " must be 1 or greater.  Please retype the value and resubmit.");
    } //end of parseServletRequest.
}
