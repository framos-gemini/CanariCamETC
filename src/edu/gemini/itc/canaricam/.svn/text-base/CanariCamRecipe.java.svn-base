// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
package edu.gemini.itc.canaricam;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import edu.gemini.itc.operation.CloudTransmissionVisitor;
import edu.gemini.itc.operation.ImageQualityCalculatable;
import edu.gemini.itc.operation.ImageQualityCalculationFactory;
import edu.gemini.itc.operation.ImagingS2NCalculatable;
import edu.gemini.itc.operation.ImagingS2NCalculationFactory;
import edu.gemini.itc.operation.NormalizeVisitor;
import edu.gemini.itc.operation.PeakPixelFluxCalc;
import edu.gemini.itc.operation.RedshiftVisitor;
import edu.gemini.itc.operation.SlitThroughput;
import edu.gemini.itc.operation.SourceFractionCalculatable;
import edu.gemini.itc.operation.SourceFractionCalculationFactory;
import edu.gemini.itc.operation.SpecS2NLargeSlitVisitor;
import edu.gemini.itc.operation.TelescopeApertureVisitor;
import edu.gemini.itc.operation.TelescopeBackgroundVisitor;
import edu.gemini.itc.operation.TelescopeTransmissionVisitor;
import edu.gemini.itc.operation.WaterTransmissionVisitor;
import edu.gemini.itc.parameters.ObservationDetailsParameters;
import edu.gemini.itc.parameters.ObservingConditionParameters;
import edu.gemini.itc.parameters.PlottingDetailsParameters;
import edu.gemini.itc.parameters.SourceDefinitionParameters;
import edu.gemini.itc.parameters.TeleParameters;
import edu.gemini.itc.shared.FormatStringWriter;
import edu.gemini.itc.shared.ITCChart;
import edu.gemini.itc.shared.ITCConstants;
import edu.gemini.itc.shared.ITCImageFileIO;
import edu.gemini.itc.shared.ITCMultiPartParser;
import edu.gemini.itc.shared.Instrument;
import edu.gemini.itc.shared.ObservationResult;
import edu.gemini.itc.shared.Recipe;
import edu.gemini.itc.shared.SEDFactory;
import edu.gemini.itc.shared.SampledSpectrumVisitor;
import edu.gemini.itc.shared.ServerInfo;
import edu.gemini.itc.shared.VisitableSampledSpectrum;
import edu.gemini.itc.shared.WavebandDefinition;
import edu.gemini.itc.shared.WriteToFile;

/**
 * This class performs the calculations for CanariCam used for imaging.
 */
public final class CanariCamRecipe implements Recipe {
	
	private static boolean _trace = true;
    // Results will be written to this PrintWriter if it is set.
    /**
	 * @uml.property  name="_out"
	 */
    private PrintWriter _out = null;  // set from servlet request
    
    // Parameters from the web page.
   
    private SourceDefinitionParameters _sdParameters;
    private ObservationDetailsParameters _obsDetailParameters;
    private ObservingConditionParameters _obsConditionParameters;
    private CanariCamParameters _ccParameters;
    private TeleParameters _teleParameters;
    private PlottingDetailsParameters _plotParameters;
    private String sigSpec;
	private String backSpec;
	private String polarizationError;
	private String singleS2N;
	private String finalS2N;
    private SpecS2NLargeSlitVisitor specS2N;
    private Calendar now = Calendar.getInstance();
    private StringBuffer _header = new StringBuffer("# CanariCam ITC: " + now.getTime()+ "\n");
    private CanaricamObsResult obsResult = new CanaricamObsResult();
    private CanariCam  instrument;
    
    public CanariCam getInstrument() {
    	return instrument;
    }
    
    public SourceDefinitionParameters getSdParameters() {
    	return _sdParameters;
    };
    
    public ObservationDetailsParameters getObsDetailParameters() {
    	return _obsDetailParameters;
    }
    
    public ObservingConditionParameters getObsConditionParameters() {
    	return _obsConditionParameters;
    }
    
    public CanariCamParameters getCCParameters() {
    	return _ccParameters;
    }
    public TeleParameters getTeleParameters () {
    	return _teleParameters;
    }
    
    public ObservationResult getCanaricamObsResult() {
    	return obsResult;
    }
    
    /**
     * Constructs a CanariCamRecipe by parsing servlet request.
     * @param r Servlet request containing form data from ITC web page.
     * @param out Results will be written to this PrintWriter.
     * @throws Exception on failure to parse parameters.
     */
    public CanariCamRecipe(HttpServletRequest r, PrintWriter out) throws Exception {
        _out = out;
        // Read parameters from the four main sections of the web page.
        _sdParameters = new SourceDefinitionParameters(r);
        _obsDetailParameters = new ObservationDetailsParameters(r);
        _obsConditionParameters = new ObservingConditionParameters(r);
        _ccParameters = new CanariCamParameters(r);
        _teleParameters = new TeleParameters(r);
        _plotParameters = new PlottingDetailsParameters(r);
    }
    
    /**
     * Constructs a CanariCamRecipe by parsing  a Multipart servlet request.
     * @param r Servlet request containing form data from ITC web page.
     * @param out Results will be written to this PrintWriter.
     * @throws Exception on failure to parse parameters.
     */
    public CanariCamRecipe(ITCMultiPartParser r, PrintWriter out) throws Exception {
        _out = out;
        // Read parameters from the four main sections of the web page.
        _sdParameters = new SourceDefinitionParameters(r);
        _obsDetailParameters = new ObservationDetailsParameters(r);
        _obsConditionParameters = new ObservingConditionParameters(r);
        _ccParameters = new CanariCamParameters(r);
        _teleParameters = new TeleParameters(r);
        _plotParameters = new PlottingDetailsParameters(r);
    }
    
    /**
     * Constructs a CanariCamRecipe given the parameters.
     * Useful for testing.
     */
    public CanariCamRecipe(SourceDefinitionParameters sdParameters,
			   ObservationDetailsParameters obsDetailParameters,
			   ObservingConditionParameters obsConditionParameters,
			   CanariCamParameters ccParameters,
			   TeleParameters teleParameters,
			   PlottingDetailsParameters plotParameters)
    {
        _sdParameters = sdParameters;
        _obsDetailParameters = obsDetailParameters;
        _obsConditionParameters = obsConditionParameters;
        _ccParameters = ccParameters;
        _teleParameters = teleParameters;
        _plotParameters = plotParameters;
    }
    
    /*
     * Caculate S2N to Imaging and Polimetry.
     */
    
    private void calcImagingAndPolimetry(	double sed_integral,
											double sky_integral,
											CanariCam instrument,
											SourceFractionCalculatable SFcalc,
											FormatStringWriter device,
											double peak_pixel_count,
											double im_qual) throws Exception {
    	
    	obsResult.setView("/itc/servlets/resultView/ImagingAndPolimetry.jsp");
    	
    	ImagingS2NCalculationFactory IS2NcalcFactory = new ImagingS2NCalculationFactory();
        ImagingS2NCalculatable IS2Ncalc =(ImagingS2NCalculatable)IS2NcalcFactory.getCalculationInstance( 
        										_sdParameters,
												_obsDetailParameters,
												_obsConditionParameters,
												_teleParameters, instrument );
        IS2Ncalc.setSedIntegral(sed_integral);
        IS2Ncalc.setSkyIntegral(sky_integral);
        IS2Ncalc.setSkyAperture(_obsDetailParameters.getSkyApertureDiameter());
        IS2Ncalc.setSourceFraction(SFcalc.getSourceFraction());
        IS2Ncalc.setNpix(SFcalc.getNPix());

        int binFactor = instrument.getSpatialBinning() * instrument.getSpatialBinning();
        IS2Ncalc.setDarkCurrent( instrument.getDarkCurrent() * binFactor );
        
        IS2Ncalc.setExtraLowFreqNoise( instrument.getExtraLowFreqNoise() );
        IS2Ncalc.calculate();
        device.setPrecision(2);
        device.clear();
        
        _println( IS2Ncalc.getTextResult(device, obsResult) );

        if( _obsDetailParameters.getCalculationMode().equals(ObservationDetailsParameters.POLARIMETRY) ) {
        	device.setPrecision(2);
        	device.clear();
        	if( _obsDetailParameters.getCalculationMethod().equals(ObservationDetailsParameters.S2N) ) {
        		// double polarizError = 0.5 / Math.sqrt( IS2Ncalc.finalSNR() / 282 );
        		double polarizError = 100 * Math.sqrt(2) / IS2Ncalc.finalSNR();
        		_println(" resulting in Polarization Error = " + device.toString( polarizError ) + " %");
        		obsResult.setPolarizError(polarizError);
        	}
        	else {
        		_println(" to achieve Polarization Error = " + device.toString(_obsDetailParameters.getPolarizError()) + " %");
        		obsResult.setPolarizError(_obsDetailParameters.getPolarizError());
        	}
        }

        _println("");
        _println( IS2Ncalc.getBackgroundLimitResult() );
        
        obsResult.setBackgroundLimitResult( IS2Ncalc.getBackgroundLimitResult() );
        
        device.setPrecision(2);
        device.clear();
        _println("");
        _println("The peak pixel signal + background is " + device.toString(peak_pixel_count)+". ");
        obsResult.setPeak_pixel_count(peak_pixel_count);
        if (peak_pixel_count > (instrument.getSaturationLimit())) {
            _println("Warning: peak pixel may be saturating the imaging deep well setting of "+ instrument.getSaturationLimit());
            obsResult.addWarnings("Warning: peak pixel may be saturating the imaging deep well setting of "+ instrument.getSaturationLimit());
        }
        
       	_println("");
        	
    }
    
    /*
     * Caculate S2N to Spectroscopy mode observation.
     */
    
    private void calcSpectroscopyAndPolimetry( 	VisitableSampledSpectrum sed,
			    								VisitableSampledSpectrum sky,
			    								CanariCam instrument, 
			    								double pixel_size,
			    								double im_qual,
			    								String ap_type,
			    								double peak_pixel_count,
			    								FormatStringWriter device,
			    								double read_noise,
			    								int number_exposures,
			    								double frac_with_source) throws Exception {
       // Calculate time exposition.
    	double exposure_time = _obsDetailParameters.getExposureTime();    //OLD EXPOSURE TIME
    	obsResult.setView("/itc/servlets/resultView/SpectroscopyAndPolimetry.jsp");        
    	if (_obsDetailParameters.getTotalObservationTime() != 0 ) {
           exposure_time = instrument.getFrameTimeDepend_F_G();
           _obsDetailParameters.setExposureTime(exposure_time);  // sets exposure time for classes that need it.
        }

        // report error if this does not come out to be an integer
        double number_source_exposures = number_exposures * frac_with_source;
        if (number_source_exposures != (int)number_source_exposures) {
            //_println("Error: "+ number_source_exposures + " " + (int)number_source_exposures);
            throw new Exception("Fraction with source value [" + 
				frac_with_source + "] * # exp. [" + number_exposures + 
				"] produces non-integral number of source exposures: " + number_source_exposures +
				" [ exp. frame time = " + exposure_time + " sec.]");
        }
        
    	 //ChartVisitor CanariCamChart = new ChartVisitor();
        ITCChart CanariCamChart = new ITCChart();
        
        SlitThroughput st;
        double sw_ap= 0.0;
        if (ap_type.equals(ObservationDetailsParameters.USER_APER)) {
            st = new SlitThroughput(im_qual, 
            						_obsDetailParameters.getApertureDiameter(), 
            						pixel_size, 
            						_ccParameters.getFPMask());
            _println("software aperture extent along slit = " + device.toString(_obsDetailParameters.getApertureDiameter()) + " arcsec");
           sw_ap = _obsDetailParameters.getApertureDiameter();
            
        } else {
            st =new SlitThroughput(im_qual,  pixel_size, _ccParameters.getFPMask());
            if (_sdParameters.getSourceGeometry().equals(SourceDefinitionParameters.EXTENDED_SOURCE)) {
                if (_sdParameters.getExtendedSourceType().equals(SourceDefinitionParameters.UNIFORM)) {
                    _println("software aperture extent along slit = " + device.toString(1/_ccParameters.getFPMask()) + " arcsec");
                    sw_ap = 1/_ccParameters.getFPMask();
                }
				} else {
				    _println("software aperture extent along slit = " + device.toString(1.4*im_qual) + " arcsec");
				    sw_ap = 1.4*im_qual;
				}
        }
        
        obsResult.setAperture(sw_ap);
        
        if(_sdParameters.getSourceGeometry().equals(SourceDefinitionParameters.POINT_SOURCE) 
        		|| _sdParameters.getExtendedSourceType().equals(SourceDefinitionParameters.GAUSSIAN) ) {
        	
        	_println("fraction of source flux in aperture = " + device.toString(st.getSlitThroughput()));
        }
        
        obsResult.setSlitThrought(st.getSlitThroughput());
        obsResult.setSourceGeometry(_sdParameters.getSourceGeometry());
        obsResult.setImqual(im_qual);
        obsResult.setSkyAperDiameter(_obsDetailParameters.getSkyApertureDiameter());
        obsResult.setTotalIntegrationTime(exposure_time* number_exposures);
        obsResult.setSouceExpTime(exposure_time * number_exposures * frac_with_source);
        
        _println("derived image size(FWHM) for a point source = " + device.toString(im_qual) + "arcsec\n");
        _println("Sky subtraction aperture = " +_obsDetailParameters.getSkyApertureDiameter() +	" times the software aperture."); 
        _println("");
        _println("Requested total integration time = " + device.toString(exposure_time* number_exposures) +
		        		" secs, of which " + device.toString(exposure_time*	number_exposures* frac_with_source) + " secs is on source." ); 
        
        _print("<HR align=left SIZE=3>");
        
        double ap_diam = st.getSpatialPix(); // ap_diam really Spec_Npix on Phil's Mathcad change later
        double spec_source_frac = spec_source_frac = st.getSlitThroughput();
        
        if (_plotParameters.getPlotLimits().equals(_plotParameters.USER_LIMITS)){
            CanariCamChart.setDomainMinMax(_plotParameters.getPlotWaveL(), _plotParameters.getPlotWaveU());
        } else {
            CanariCamChart.autoscale();
        }
        
        //For the usb case we want the resolution to be determined by the
        //slit width and not the image quality for a point source.
        if (_sdParameters.getSourceGeometry().equals(SourceDefinitionParameters.EXTENDED_SOURCE)){
            if (_sdParameters.getExtendedSourceType().equals(SourceDefinitionParameters.UNIFORM)) {
                im_qual=10000;
                
                if (ap_type.equals(ObservationDetailsParameters.USER_APER)) {
                    spec_source_frac = _ccParameters.getFPMask()*ap_diam*pixel_size;  //ap_diam = Spec_NPix
                } else if (ap_type.equals(ObservationDetailsParameters.AUTO_APER)) {
                    ap_diam = new Double(1/(_ccParameters.getFPMask() * pixel_size)+0.5).intValue();
                    spec_source_frac = 1;
                }
            }
        }
        specS2N = new SpecS2NLargeSlitVisitor( _ccParameters.getFPMask(), 
				            					pixel_size,
				            					instrument.getSpectralPixelWidth(),
				            					instrument.getObservingStart(),
				            					instrument.getObservingEnd(),
				            					instrument.getGratingDispersion_nm(),
				            					instrument.getGratingDispersion_nmppix(),
				            					instrument.getGratingResolution(),
				            					spec_source_frac, 
				            					im_qual,
				            					ap_diam, 
				            					number_exposures,
				            					frac_with_source, 
				            					exposure_time,
				            					instrument.getDarkCurrent() * instrument.getSpatialBinning() *	instrument.getSpectralBinning(),
				            					read_noise,
				            					_obsDetailParameters.getSkyApertureDiameter(),
				            					instrument.getSpectralBinning() );

        specS2N.setDetectorTransmission(instrument.getDetectorTransmision());
        specS2N.setSourceSpectrum(sed);
        specS2N.setBackgroundSpectrum(sky);
        sed.accept(specS2N);
        _println("<p style=\"page-break-inside: never\">");
        
        CanariCamChart.addArray(specS2N.getSignalSpectrum().getData(), "Signal ");
        CanariCamChart.addArray(specS2N.getBackgroundSpectrum().getData(), "SQRT(Background)  ");
        CanariCamChart.addTitle("Signal and Background ");
        CanariCamChart.addxAxisLabel("Wavelength (nm)");
        CanariCamChart.addyAxisLabel("e- per exposure per spectral pixel");
        _println(CanariCamChart.getBufferedImage(), "SigAndBack");
        _println("");
        
        sigSpec = _printSpecTag("ASCII signal spectrum", "SigAndBack");
        backSpec = _printSpecTag("ASCII background spectrum", "SigAndBack");
        CanariCamChart.flush();
       
        
        if (_obsDetailParameters.getCalculationMode().equals(ObservationDetailsParameters.SPECTROPOLARIMETRY)) {
        	
        	VisitableSampledSpectrum spectroPolimetry = (VisitableSampledSpectrum) specS2N.getFinalS2NSpectrum().clone();
        	for(int i = 0; i < spectroPolimetry.getLength();++i)
        		if ( Math.sqrt(spectroPolimetry.getY(i) / 282) > 0) {
        			// double polarError = 0.5 / Math.sqrt(spectroPolimetry.getY(i) / 282);  // Calc before.
        			double polarizError = 100 * Math.sqrt(2) / spectroPolimetry.getY(i);
        			spectroPolimetry.setY(i, polarizError);	
        		}
        	
        	specS2N.setSpectroPolimetry(spectroPolimetry);
        	
        	double buffer [][] = spectroPolimetry.getData();
        	int j = 0;
        	while (j < buffer[1].length && buffer[1][j] == 0)
        		++j;
        	for (int i = j; i >= 0; --i)
        		buffer[1][i] = buffer[1][j];
        	
        	double min = 9999999.9999;
        	double max = -9999999.9999;
        	for (int i = 0; i < buffer[1].length; ++i) 
        		if ( (i < buffer[0].length) && (buffer[0][i] > 8000) && (buffer[0][i] < 13000) ) {
        			min = (min > buffer[1][i]) ? buffer[1][i] : min;
        			max = (max < buffer[1][i]) ? buffer[1][i] : max;
        		}
        	
        	_println("");
            _println("");
        	CanariCamChart.addArray(buffer, "Final  Polarization Error", new Color(0, 0, 205));
        	CanariCamChart.addTitle("Final Polarization Error (%)");
        	CanariCamChart.addyAxisLabel("Polarization Error (%)");
        	CanariCamChart.setRangeMinMax(min*0.95, max*1.10);
        	_println(CanariCamChart.getBufferedImage(),"Polarization");
            _println("");
            CanariCamChart.addxAxisLabel("Wavelength (nm)");
            polarizationError = _printSpecTag("Polarization error data", "Polarization");
            CanariCamChart.flush();
            
        }
        _println("");
        _println("");
        CanariCamChart.addArray(specS2N.getExpS2NSpectrum().getData(), "Single Exp S/N", new Color(255, 0, 0));
    	CanariCamChart.addArray(specS2N.getFinalS2NSpectrum().getData(), "Final S/N  ", new Color(0, 0, 205) );
        CanariCamChart.addTitle("Intermediate Single Exp and Final S/N");
        CanariCamChart.addyAxisLabel("Signal / Noise per spectral pixel");
        CanariCamChart.autoscale();
        _println(CanariCamChart.getBufferedImage(),"Sig2N");
        _println("");
        CanariCamChart.addxAxisLabel("Wavelength (nm)");
        singleS2N = _printSpecTag("Single Exposure S/N ASCII data", "Sig2N");
        finalS2N = _printSpecTag("Final S/N ASCII data", "Sig2N");
        
        CanariCamChart.flush();       
    }
    
    /*
     * Calculate peak pixel flux
     */
    
    public double calcPeakPixelFlux (double im_qual, double exp_time, double sed_integral, double sky_integral, CanariCam instrument, SourceFractionCalculatable SFcalc) throws Exception {
    	PeakPixelFluxCalc ppfc;
        if( _sdParameters.getSourceGeometry().equals(SourceDefinitionParameters.POINT_SOURCE) 
        	||_sdParameters.getExtendedSourceType().equals(SourceDefinitionParameters.GAUSSIAN) ) {
            
            ppfc = new PeakPixelFluxCalc( im_qual, 
            							  instrument.getPixelSize(),
										  exp_time, 
										  sed_integral, 
										  sky_integral,
										  instrument.getDarkCurrent() );
            
            return ppfc.getFluxInPeakPixel();
        }
        
        if (_sdParameters.getExtendedSourceType().equals(SourceDefinitionParameters.UNIFORM)) {
            ppfc = new PeakPixelFluxCalc( im_qual, 
            							  instrument.getPixelSize(),
            							  exp_time, 
            							  sed_integral, 
            							  sky_integral,
            							  instrument.getDarkCurrent() );
            return ppfc.getFluxInPeakPixelUSB( SFcalc.getSourceFraction(), SFcalc.getNPix() );
        }
        
        throw new Exception("Peak Pixel could not be calculated ");
    }
    
    /*
     * Print result common.
     */
    
    private void printResultCommon (FormatStringWriter device, CanariCam instrument) {
    	_println("");
        device.setPrecision(2);  // TWO decimal places
        device.clear();
        StringBuffer buffer = new StringBuffer();
        _print("<HR align=left SIZE=3>");
        _println("<b>Input Parameters:</b>");
        _println("Instrument: "+ instrument.getName()+"\n");
        _println(_sdParameters.printParameterSummary());
        _println(instrument.toString());
        _println(_obsConditionParameters.printParameterSummary());
        _println(_obsDetailParameters.printParameterSummary());
        
        buffer.append(instrument.getName()+"\n");
        buffer.append(_sdParameters.printParameterSummary());
        buffer.append(instrument.toString());
        buffer.append(_obsConditionParameters.printParameterSummary());
        buffer.append(_obsDetailParameters.printParameterSummary());
        obsResult.setInputParameters(buffer.toString());
    }
    
    /*
     * Return time exposition. 
     */
    
    public double getExpTime (double frameTime) {
    	 double exp_time = _obsDetailParameters.getExposureTime();
         if (_obsDetailParameters.getTotalObservationTime() != 0) {
             exp_time = frameTime;
             _obsDetailParameters.setExposureTime(exp_time);
         }
         return exp_time;
    }
   
    
    /**
     * Performes recipe calculation and writes results to a cached PrintWriter or to System.out.
     * @throws Exception A recipe calculation can fail in many ways,
     * missing data files, incorrectly-formatted data files, ...
     */
    public void writeOutput() throws Exception {
        _println("");
        
        if (_trace) {
        	File directory = new File("/tmp");
        	String [] files = directory.list();
        	for (String f : files) {
        			new File("/tmp/"+f).delete();
        	}
        }
                
        // This object is used to format numerical strings.
        FormatStringWriter device = new FormatStringWriter();
        device.setPrecision(4);  // Two decimal places
        device.clear();
        
        instrument = new CanariCam(_ccParameters, _obsDetailParameters);
        
        // Module 1b
        // Define the source energy (as function of wavelength).
        // Spectral energy distribution.
        VisitableSampledSpectrum sed = SEDFactory.getSED(_sdParameters, instrument);
        //sed.applyWavelengthCorrection();
       
       	// Calculate red shift
        // inputs: instrument, SED
        // calculates: redshifted SED
        // output: redshifteed SED
        SampledSpectrumVisitor redshift = new RedshiftVisitor(_sdParameters.getRedshift());
        sed.accept(redshift);
        //writeToFile.write("source-RedShift.txt", sed.getData());
        
        // Must check to see if the redshift has moved the spectrum beyond
        // useful range.  The shifted spectrum must completely overlap
        // both the normalization waveband and the observation waveband
        // (filter region).
        
        //any sed except BBODY and ELINE have normailization regions
        if( !(_sdParameters.getSpectrumResource().equals(_sdParameters.ELINE) 
        		|| _sdParameters.getSpectrumResource().equals(_sdParameters.BBODY)) ) {
        	 
             double start = WavebandDefinition.getStart(_sdParameters.getNormBand());
             double end = WavebandDefinition.getEnd(_sdParameters.getNormBand());
             if (sed.getStart() > start || sed.getEnd() < end) {
            	 throw new Exception("Shifted spectrum lies outside of specified normalisation waveband.");
             }
        }	
        
   
        if (_plotParameters.getPlotLimits().equals(_plotParameters.USER_LIMITS)) {
            if(_plotParameters.getPlotWaveL() > instrument.getObservingEnd() 
            		|| _plotParameters.getPlotWaveU() < instrument.getObservingStart()) {
                _println(" The user limits defined for plotting do not overlap with the Spectrum.");
                throw new Exception("User limits for plotting do not overlap with filter.");
            }
        }
        
        // Module 2
        // Convert input into standard internally-used units.
        //
        // inputs: instrument,redshifted SED, waveband, normalization flux, units
        // calculates: normalized SED, resampled SED, SED adjusted for aperture
        // output: SED in common internal units
        
        if (!_sdParameters.getSpectrumResource().equals(_sdParameters.ELINE)) {
        	SampledSpectrumVisitor norm = new NormalizeVisitor(_sdParameters.getNormBand(),
															   _sdParameters.getSourceNormalization(),
															   _sdParameters.getUnits());
            sed.accept(norm);
        }
 
        
        SampledSpectrumVisitor tel = new TelescopeApertureVisitor();
        sed.accept(tel);
        
        // SED is now in units of photons/s/nm
        // Module 3b
        // The atmosphere and telescope modify the spectrum and
        // produce a background spectrum.
        //
        // inputs: SED, AIRMASS, sky emmision file, mirror configuration,
        // output: SED and sky background as they arrive at instruments
        
        SampledSpectrumVisitor clouds = new CloudTransmissionVisitor(_obsConditionParameters.getSkyTransparencyCloud());
        sed.accept(clouds);
        
        //For mid-IR observation the watervapor percentile and sky background
        //   percentile must be the same
        if(_obsConditionParameters.getSkyTransparencyWaterCategory() !=_obsConditionParameters.getSkyBackgroundCategory()) {
            _println("");
            _println("Sky background percentile must be equal to sky transparency(water vapor): \n "+
		     "    Please modify the Observing condition constraints section of the HTML form \n"+
		     "    and recalculate.");
            throw new Exception("");
        }
        
        SampledSpectrumVisitor water = new WaterTransmissionVisitor(
        								_obsConditionParameters.getSkyTransparencyWater(),
        								_obsConditionParameters.getAirmass(),
        								"PNtrans_nq_",
        								ITCConstants.ORM, 
        								ITCConstants.MID_IR);
        
        sed.accept(water);
        VisitableSampledSpectrum sky = SEDFactory.getSED(
							        		  			"/" + ITCConstants.HI_RES + "/" + 
							        		  			ITCConstants.ORM+ ITCConstants.MID_IR +
							        		  			ITCConstants.SKY_BACKGROUND_LIB + "/" +
							        		  			ITCConstants.MID_IR_SKY_BACKGROUND_FILENAME_ORM + "_"
							        		  			+ _obsConditionParameters.getSkyBackgroundCategoryORM() +
							        		  			"_" + _obsConditionParameters.getAirmassCategory() +
							        		  			"_ph"+ ITCConstants.DATA_SUFFIX,
							        		  			instrument.getSampling());
        
        // Apply telescope transmission to both sed and sky
        SampledSpectrumVisitor t = new TelescopeTransmissionVisitor(
        									_teleParameters.getMirrorCoating(),
        									_teleParameters.getInstrumentPort());
        sed.accept(t);
        sky.accept(t);
       	
       	//Create and Add background for the telescope.
        SampledSpectrumVisitor tb = new TelescopeBackgroundVisitor(_teleParameters.getMirrorCoating(),
																   _teleParameters.getInstrumentPort(),
																   ITCConstants.MID_IR_TELESCOPE_BACKGROUND_FILENAME_BASE, 
																   ITCConstants.CERRO_PACHON, ITCConstants.MID_IR);
        sky.accept(tb);
        sky.accept(tel);

       	// Add instrument background to sky background for a total background.
        // At this point "sky" is not the right name.
        instrument.addBackground(sky);

        // Module 4  AO module not implemented
        // The AO module affects source and background SEDs.
        
        // Module 5b
        // The instrument with its detectors modifies the source and
        // background spectra.
        // input: instrument, source and background SED
        // output: total flux of source and background.
        
        instrument.convolveComponents(sed);
        instrument.convolveComponents(sky);

        // For debugging, print the spectrum integrals.
        double sed_integral = sed.getIntegral();
        double sky_integral = sky.getIntegral();
        
        // End of the Spectral energy distribution portion of the ITC.
        // Start of morphology section of ITC
        
        // Module 1a
        // The purpose of this section is to calculate the fraction of the
        // source flux which is contained within an aperture which we adopt
        // to derive the signal to noise ratio.  There are several cases
        // depending on the source morphology.
        // Define the source morphology
        //
        // inputs: source morphology specification
        
        
        // Calculate image quality
        ImageQualityCalculationFactory IQcalcFactory = new ImageQualityCalculationFactory();
        IQcalcFactory.optionUF = false;
        ImageQualityCalculatable IQcalc = (ImageQualityCalculatable) IQcalcFactory.getCalculationInstance(_sdParameters,
							        																	  _obsDetailParameters,
							        																	  _obsConditionParameters,
							        																	  _teleParameters, 
							        																	  instrument);
        IQcalc.calculate();
        
        
        // Calculate the Fraction of source in the aperture
        SourceFractionCalculationFactory SFcalcFactory = new SourceFractionCalculationFactory();
        SourceFractionCalculatable SFcalc =(SourceFractionCalculatable) SFcalcFactory.getCalculationInstance( _sdParameters,
														    												  _obsDetailParameters,
														    											      _obsConditionParameters,
														    												  _teleParameters, instrument );
        double im_qual= IQcalc.getImageQuality();
        SFcalc.setImageQuality(im_qual);
        SFcalc.calculate();

        obsResult.setObsMode(_obsDetailParameters.getCalculationMode());
        
        if (_obsDetailParameters.getCalculationMode().equals(ObservationDetailsParameters.IMAGING)) {
            _print(SFcalc.getTextResult(device, obsResult));
    		          
            _println(IQcalc.getTextResult(device, obsResult));
            _println("Sky subtraction aperture = " + _obsDetailParameters.getSkyApertureDiameter()  +" times the software aperture.\n");
            obsResult.setSkyAperDiameter(_obsDetailParameters.getSkyApertureDiameter());
        }
        
        // Calculate the Peak Pixel Flux
        double exp_time  = getExpTime (instrument.getFrameTimeDepend_F_G());
        double peak_pixel_count = calcPeakPixelFlux(im_qual, exp_time, sed_integral, sky_integral, instrument, SFcalc);
        
        // In this version we are bypassing morphology modules 3a-5a.
        // i.e. the output morphology is same as the input morphology.
        // Might implement these modules at a later time.
        
        double frac_with_source = _obsDetailParameters.getSourceFraction();
        int number_exposures = _obsDetailParameters.getNumExposures();  //OLD NUM EXPOSURE
        if (_obsDetailParameters.getTotalObservationTime() != 0) {
            //getFrameTime
            number_exposures = new Double(_obsDetailParameters.getTotalObservationTime() / instrument.getFrameTimeDepend_F_G() + 0.5).intValue();
            double number_source_exposures = number_exposures * frac_with_source;
            if (number_source_exposures != (int)number_source_exposures) 
            	++number_exposures;
            
            _obsDetailParameters.setNumExposures(number_exposures);  //sets number exposures for classes that need it.
        }
        
        //ObservationMode Imaging (default), spectroscopy, or polarimetry:
        if ( _obsDetailParameters.getCalculationMode().equals(ObservationDetailsParameters.SPECTROSCOPY) 
        	 || _obsDetailParameters.getCalculationMode().equals(ObservationDetailsParameters.SPECTROPOLARIMETRY)) {
        	
            
        	calcSpectroscopyAndPolimetry(sed, 
			        					 sky, 
			        					 instrument, 
			        					 instrument.getPixelSize(), 
			        					 im_qual,
			        					 _obsDetailParameters.getApertureType(), 
			        					 peak_pixel_count, 
			        					 device, 
									     instrument.getReadNoise(), 
									     number_exposures,
										 frac_with_source);
        		
        	_println("");
         	_println("");
         	_println("");
         	
         	printResultCommon (device, instrument);
         	
         	_println(_plotParameters.printParameterSummary());
           	_println( specS2N.getSignalSpectrum() , sigSpec);
        	_println( specS2N.getBackgroundSpectrum() , backSpec);
        	if (_obsDetailParameters.getCalculationMode().equals(ObservationDetailsParameters.SPECTROPOLARIMETRY))
        		_println( specS2N.getSpectroPolimetry(), polarizationError);
        	_println(specS2N.getExpS2NSpectrum()  , singleS2N);
        	_println( specS2N.getFinalS2NSpectrum() , finalS2N);
        }    
        else {
            calcImagingAndPolimetry(sed_integral,
									sky_integral,
									instrument,
									SFcalc,
									device,
									peak_pixel_count,
									im_qual);
            printResultCommon (device, instrument);
        }
    }
    
    // Prints string to implied destination.  If _out is null, prints to
    //System.out otherwise prints to _out PrintWriter with html line breaks.
    
    private void _println(String s) {
        if (_out == null) {
            System.out.println(replace(s,"<br>","\n"));
        } else {
            _out.println(replace(s,"\n","<br>") + "<br>");
        }
        
        s= replace(s,"<br>","\n");
        s= replace(s,"<BR>","\n");
        s= replace(s,"<LI>","-");
        s= replace(s,"\n","\n#");
        
        if(!s.equals(""))
            if (s.charAt(0)!='<')
                _header.append("# "+s + "\n");
    }
    
    // Prints string to implied destination.  If _out is null, prints to
    //System.out otherwise prints to _out PrintWriter.
    private void _print(String s) {
        if (_out == null) {
            System.out.print(replace(s,"<br>","\n"));
        } else {
            _out.print(replace(s,"\n","<br>"));
        }
        s= replace(s,"<br>","\n");
        s= replace(s,"<BR>","\n");
        s= replace(s,"<LI>","-");
        
        if(!s.equals(""))
            if (s.charAt(0)!='<')
                _header.append("# "+s  + "\n");
    }
    
    private void _println(VisitableSampledSpectrum sed, String spectrumName) {
        // this will print out the VisitableSampled Spectrum as a text file to be taken by the user
        ITCImageFileIO FileIO = new ITCImageFileIO();
        
        try{
            FileIO.saveSedtoDisk(_header.toString(), sed, spectrumName);
        } catch (Exception ex) { 
        	System.out.println("Unable to save file");
        	ex.printStackTrace();
        }
        
    }
    
    private void _println(BufferedImage image, String imageName) {
        ITCImageFileIO FileIO = new ITCImageFileIO();
        
        try{
            FileIO.saveCharttoDisk(image);
        } catch (Exception ex) { 
        	System.out.println("Unable to save file");
        	ex.printStackTrace();
        }
        
        _print("<IMG alt=\""+FileIO.getFileName() + "\" height=500 src=\"" + ServerInfo.getServerURL() +
        		"itc/servlet/images?type=img&filename=" + FileIO.getFileName()+"\" width=675 border=0>");
        
        obsResult.addSouceCharts(imageName,"<IMG alt=\""+FileIO.getFileName() + "\" height=500 src=\"" + ServerInfo.getServerURL() +
        		"itc/servlet/images?type=img&filename=" + FileIO.getFileName()+"\" width=675 border=0>" );
        
        
    }
    
    private String _printSpecTag(String spectrumName) {
        String Filename = "";
        ITCImageFileIO FileIO = new ITCImageFileIO();
        try {
            Filename = FileIO.getRandomFileName(".dat");
            _println("<a href =" + "\""+ServerInfo.getServerURL()+"itc/servlet/images?type=txt&filename="+
            		Filename+"\"" +"> Click here for "+spectrumName+". </a>");
        } catch (Exception ex) { System.out.println("Unable to get random file");ex.printStackTrace();}
        
        obsResult.addLinkSpectrum(spectrumName, "<a href =" + "\"" + ServerInfo.getServerURL() + 
        							"itc/servlet/images?type=txt&filename=" + Filename+"\"" +"> Click here for "+spectrumName+". </a>");
        return Filename;
    }
    
    private String _printSpecTag(String spectrumName, String chartName) {
        String Filename = "";
        ITCImageFileIO FileIO = new ITCImageFileIO();
        try {
            Filename = FileIO.getRandomFileName(".dat");
            _println("<a href =" + "\""+ServerInfo.getServerURL()+"itc/servlet/images?type=txt&filename="+
            		Filename+"\"" +"> Click here for "+spectrumName+". </a>");
        } catch (Exception ex) { System.out.println("Unable to get random file");ex.printStackTrace();}
        
        obsResult.addLinkSpectrum(chartName, "<a href =" + "\"" + ServerInfo.getServerURL() + 
        							"itc/servlet/images?type=txt&filename=" + Filename+"\"" +"> Click here for "+spectrumName+". </a>");
        return Filename;
    }
    
    static String replace(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();
        
        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e+pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }
    
	@Override
	public ObservationResult getObsResult() throws Exception {
		
		return getCanaricamObsResult();
	}
}
