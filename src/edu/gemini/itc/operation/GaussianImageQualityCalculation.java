package edu.gemini.itc.operation;

import edu.gemini.itc.shared.FormatStringWriter;
import edu.gemini.itc.shared.ObservationResult;

public class GaussianImageQualityCalculation implements ImageQualityCalculatable {

    /**
	 * @uml.property  name="im_qual"
	 */
    double im_qual;
	/**
	 * @uml.property  name="fwhm"
	 */
	double fwhm;

    public GaussianImageQualityCalculation(
            double fwhm) {
        this.fwhm = fwhm;
    }

    public void calculate() {
        im_qual = fwhm;
    }

    public String getTextResult(FormatStringWriter device, ObservationResult obsResult) {
    	obsResult.setImqual(im_qual);
        return "derived image size for source = " + device.toString(im_qual) + "\n";
    }

    public double getImageQuality() {
        return im_qual;
    }
}
