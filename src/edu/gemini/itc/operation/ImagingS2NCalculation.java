package edu.gemini.itc.operation;

import edu.gemini.itc.shared.FormatStringWriter;
import edu.gemini.itc.shared.ObservationResult;

public abstract class ImagingS2NCalculation implements ImagingS2NCalculatable
{
    /**
	 * @uml.property  name="var_source"
	 */
    double var_source;

	/**
	 * @uml.property  name="var_background"
	 */
	double var_background;

	/**
	 * @uml.property  name="var_dark"
	 */
	double var_dark;

	/**
	 * @uml.property  name="var_readout"
	 */
	double var_readout;

	/**
	 * @uml.property  name="noise"
	 */
	double noise;

	/**
	 * @uml.property  name="sourceless_noise"
	 */
	double sourceless_noise;

	/**
	 * @uml.property  name="signal"
	 */
	double signal;

	/**
	 * @uml.property  name="sed_integral"
	 */
	double sed_integral;

	/**
	 * @uml.property  name="source_fraction"
	 */
	double source_fraction;

	/**
	 * @uml.property  name="npix"
	 */
	double Npix;

	/**
	 * @uml.property  name="sky_integral"
	 */
	double sky_integral;

	/**
	 * @uml.property  name="read_noise"
	 */
	double read_noise;

	/**
	 * @uml.property  name="dark_current"
	 */
	double dark_current;

	/**
	 * @uml.property  name="pixel_size"
	 */
	double pixel_size;

	/**
	 * @uml.property  name="exposure_time"
	 */
	double exposure_time;

	/**
	 * @uml.property  name="noiseFactor"
	 */
	double noiseFactor;
        
    /**
	 * @uml.property  name="secondary_integral"
	 */
    double secondary_integral=0;
    /**
	 * @uml.property  name="secondary_source_fraction"
	 */
    double secondary_source_fraction=0;
        
    /**
	 * @uml.property  name="skyAper"
	 */
    double skyAper =1;
        
    //Extra Low frequency noise.  Default:  Has no effect.
    /**
	 * @uml.property  name="elfinParam"
	 */
    int elfinParam = 1;

    public void calculate() throws Exception{
		noiseFactor = 1+ (1/skyAper);
		var_source = sed_integral * source_fraction * exposure_time;
        var_source = var_source + secondary_integral * secondary_source_fraction * exposure_time;
                
        //Multiply source by Extra-Low Frequency Noise
        var_source = var_source * elfinParam;  
		var_background = sky_integral * exposure_time * pixel_size * pixel_size * Npix;
        //Multiply background by Extra-Low Frequency Noise
        var_background = var_background * elfinParam;
		var_dark = dark_current * Npix * exposure_time;
		var_readout = read_noise * read_noise * Npix;
		noise = Math.sqrt(var_source + var_background + var_dark + var_readout);
		sourceless_noise = Math.sqrt(var_background + var_dark + var_readout);
		signal = sed_integral * source_fraction * exposure_time + secondary_integral * secondary_source_fraction * exposure_time;
	}

	public void setSedIntegral(double sed_integral) {
		this.sed_integral = sed_integral;
	}
        
        public void setSecondaryIntegral(double secondary_integral) {
                this.secondary_integral = secondary_integral;
        }
        
        public void setSecondarySourceFraction(double secondary_source_fraction) {
              this.secondary_source_fraction = secondary_source_fraction;
        }

	public void setSourceFraction(double source_fraction) {
		this.source_fraction = source_fraction;
	}

	public void setDarkCurrent(double dark_current) {
		this.dark_current = dark_current;
	}

	/**
	 * @param Npix
	 * @uml.property  name="npix"
	 */
	public void setNpix(double Npix) { this.Npix = Npix; }

	public void setSkyAperture(double skyAper) { this.skyAper = skyAper; }

	public void setSkyIntegral(double sky_integral) {
		this.sky_integral = sky_integral;
	}
        //method to set the extra low freq noise.
        public void setExtraLowFreqNoise(int extraLowFreqNoise) {
                this.elfinParam = extraLowFreqNoise;
        }
        
    public double finalSNR() { return signal/noise; }

    public String getTextResult(FormatStringWriter device, ObservationResult obsResult) {
		StringBuffer sb = new StringBuffer();
		sb.append("Contributions to total noise (e-) in aperture (per exposure):\n");
   		sb.append("Source noise = "       + device.toString(Math.sqrt(var_source))+"\n");
   		sb.append("Background noise = "   + device.toString(Math.sqrt(var_background))+"\n");
   		sb.append("Dark current noise = " + device.toString(Math.sqrt(var_dark))+"\n");
   		sb.append("Readout noise = "      + device.toString(Math.sqrt(var_readout))+"\n\n");
 		sb.append("Total noise per exposure = "  + device.toString(noise) +"\n");
   		sb.append("Total signal per exposure = " + device.toString(signal) +"\n\n");
   		obsResult.setVar_source(Math.sqrt(var_source));
   		obsResult.setVar_background(Math.sqrt(var_background));
   		obsResult.setVar_dark(Math.sqrt(var_dark));
   		obsResult.setVar_readout(Math.sqrt(var_readout));
   		obsResult.setNoise(noise);
   		obsResult.setSignal(signal);
		return sb.toString();
	}

	public String getBackgroundLimitResult() {
		   if (Math.sqrt(var_source + var_dark +var_readout) >Math.sqrt(var_background))
		       return "Warning: observation is NOT background noise limited";
		   else return "Observation is background noise limited.";
	}
		
	public double getNpix() {
		return Npix;
	}

	public double getSkyAperture() {
		return skyAper;
	}
            
    public double getSourceFraction() {
    	return source_fraction;
    }
    
    public double getVarSource() {
    	return var_source;
    }
        
    public double getVarBackground() {
    	return var_background;
    }
        
    public double getVarDark() {
    	return var_dark;
    }
    
    public double getVarReadOut() {
    	return var_readout;
    }
    
    public double getSourcelessNoise() {
    	return var_readout;
    }
    
    public double getNoiseFactor() {
    	return noiseFactor;
    }
 
    public double getNoise() {
    	return noise;
    }
    
    public double getSignal() {
    	return signal;
    }
    
    
}
