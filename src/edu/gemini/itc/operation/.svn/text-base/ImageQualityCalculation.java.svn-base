package edu.gemini.itc.operation;

import edu.gemini.itc.shared.FormatStringWriter;
import edu.gemini.itc.shared.ArraySpectrum;
import edu.gemini.itc.shared.DefaultArraySpectrum;
import edu.gemini.itc.shared.ITCConstants;
import edu.gemini.itc.shared.ObservationResult;

public class ImageQualityCalculation implements ImageQualityCalculatable {

    /**
	 * @uml.property  name="wfs"
	 */
    String wfs;
	/**
	 * @uml.property  name="imageQuality"
	 */
	String imageQuality;
	/**
	 * @uml.property  name="im_qual_model_file"
	 */
	String im_qual_model_file;
    /**
	 * @uml.property  name="airmass"
	 */
    double airmass;
	/**
	 * @uml.property  name="effectiveWavelength"
	 */
	double effectiveWavelength;
	/**
	 * @uml.property  name="im_qual"
	 */
	double im_qual;
	
	public boolean optionUF = false;

    public ImageQualityCalculation(String wfs,
                                   int imageQuality,
                                   double airmass,
                                   int effectiveWavelength,
                                   boolean optionUF) {

    	im_qual_model_file = ITCConstants.IM_QUAL_LIB + "/" +
                ITCConstants.IM_QUAL_BASE + wfs + imageQuality +
                ITCConstants.DATA_SUFFIX;
    	
    	if (optionUF) {
    		String aux = im_qual_model_file.substring(0, im_qual_model_file.length() - 4);
    		im_qual_model_file = aux+"_GEMINI.dat";
    	}
    	

        this.airmass = airmass;
        this.effectiveWavelength = effectiveWavelength;
    }

    public void calculate() throws Exception {
    	ArraySpectrum im_qual_model = new DefaultArraySpectrum(im_qual_model_file);
    	im_qual = im_qual_model.getY( (double) effectiveWavelength) * (Math.pow(airmass, 0.6));
    }

    public String getTextResult(FormatStringWriter device, ObservationResult obsResult) {
    	obsResult.setImqual(im_qual);
        return "derived image size (FWHM) for a point source = " + device.toString(im_qual) + " arcsec.\n";
    }

    public double getImageQuality() {
        return im_qual;
    }
}
