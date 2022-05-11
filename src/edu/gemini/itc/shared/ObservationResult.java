package edu.gemini.itc.shared;

import java.text.DecimalFormat;

public abstract class ObservationResult {
	private double noise;
	private double signal;
	private double var_readout;
	private double var_background;
	private double var_dark;
	private double var_source;
	private double polarizError;
	private String backgroundLimitResult;
	private double peak_pixel_count;
	private double saturationLimit;
	private double aperture;
	private double sourceFranction;
	private double nPix;
	private double imqual;
	private double skyAperDiameter;
	private double numberExposures;
	private double totalIntegrationTime;
	private double timeOfSource;
	private double numExpFracSource;
	private double effective_s2n;
	private double expTime;
	private double srcExpTime;
	private double intermediateS2N;
	private double finalS2n;
	private String view;
	private char method;
	private String inputParameters;
	
	/*
	 * Public section
	 */
	
	public double getNumberExposures() {

		return numberExposures;
	}
	
	public double getTotalIntegrationTime () {
		
		return totalIntegrationTime;
	}
	public double getTimeOfSource() {
		return timeOfSource;
	}
	
	public double getNumExpFracSource() {
		return numExpFracSource;
	}
	
	public double getEffectiveS2n() {
		return effective_s2n;
	}
	public double getExpTime() {
		return expTime;
	}
	
	public double getSrcExpTime() {
		return srcExpTime;
	}
	
	public double getIntermediateS2N() {
		return intermediateS2N;
	}
	
	public double getFinalS2n() {
		return finalS2n;
	}
	
	public double getSourceFranction() {
		return sourceFranction;
	}

	public double getnPix() {
		return nPix;
	}
	
	public double getSkyAperDiameter() {
		return skyAperDiameter;
	}

	public double getSaturationLimit() {
		return saturationLimit;
	}

	public double getAperture() {
		return aperture;
	}

	public double getImqual() {
		return imqual;
	}
	
	public double getNoise() {
		return noise;
	}	 
	
    public double getSignal() {
		return signal;
	}
        
	public double getVar_readout() {
		return var_readout;
	}
		
	public double getVar_background() {
		return var_background;
	}
		
	public double getVar_dark() {
		return var_dark;
	}
	
	public double getVar_source() {
		return var_source;
	}
	
	public double getPolarizError() {
		return polarizError;
	}

	public String getBackgroundLimitResult() {
		return backgroundLimitResult;
	}

	public double getPeak_pixel_count() {
		return peak_pixel_count;
	}
	
	public double getMethod() {
		return method;
	}

	public void setSourceFranction(double sourceFranction) {
		this.sourceFranction = sourceFranction;
	}
	
	public void setView(String view) {
		this.view = view;
	}
	
	
	public String getView() {
		return view;
	}
	
	public String getInputParameters() {
		return inputParameters;
	}
	

	public void setInputParameters(String inputParameters) {
		this.inputParameters = inputParameters;
	}
	
	
	public void setnPix(double nPix) {
		this.nPix = nPix;
	}
	public void setSkyAperDiameter(double skyAperDiameter) {
		this.skyAperDiameter = skyAperDiameter;
	}
    
    public void setNoise(double noise) {
		this.noise = noise;
	}	
	
	public void setSignal(double signal) {
		this.signal = signal;
	}
	
	public void setVar_readout(double var_readout) {
		this.var_readout = var_readout;
	}
	
	public void setVar_background(double var_background) {
		this.var_background = var_background;
	}
	
	public void setVar_dark(double var_dark) {
		this.var_dark = var_dark;
	}
	
	public void setVar_source(double var_source) {
		this.var_source = var_source;
	}

	public void setPolarizError(double polarizError) {
		this.polarizError = polarizError;
		
	}

	public void setBackgroundLimitResult(String backgroundLimitResult) {
		this.backgroundLimitResult = backgroundLimitResult;
	}
	
	
	public void setPeak_pixel_count(double peak_pixel_count) {
		this.peak_pixel_count = peak_pixel_count;
	}

	public void setSaturationLimit(double saturationLimit) {
		this.saturationLimit = saturationLimit;
	}

	public void setAperture(double swAp) {
		this.aperture = swAp;
	}

	public void setSourceFraction(double sourceFraction) {
		
		this.sourceFranction = sourceFraction;
	}

	public void setNpix(double nPix) {
		
		this.nPix = nPix;
	}

	public void setImqual(double imageQuality) {
		this.imqual = imageQuality;
		
	}

	public void skyApertureDiameter(double skyApertureDiameter) {
		this.skyAperDiameter = skyApertureDiameter;
		
	}

	public void setNumberOfExposures(double req_number_exposures) {
		this.numberExposures = req_number_exposures;
		
	}

	public void setNumExposureFracSource(double d) {
		this.numExpFracSource = d;
	}

	public void setTotalIntegrationTime(double d) {
		this.totalIntegrationTime = d;
		
	}

	public void setTimeOfSource(double d) {
		this.timeOfSource = d;
	}

	public void setEffectiveS2N(double effective_s2n) {
		this.effective_s2n = effective_s2n;
	}

	public void setExpTime(double totalExpTime) {
		this.expTime = totalExpTime;
	}

	public void setSouceExpTime(double srcExpTime) {
		this.srcExpTime = srcExpTime;
	}

	public void setIntermediateS2N(double exp_s2n) {
		this.intermediateS2N = exp_s2n;
	}

	public void setFinalS2N(double final_s2n) {
		this.finalS2n = final_s2n;
	}

	public void setMethod(char c) {
		this.method = c;		
	}
}
