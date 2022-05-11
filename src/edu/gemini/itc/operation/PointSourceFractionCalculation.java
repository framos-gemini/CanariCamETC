package edu.gemini.itc.operation;

import edu.gemini.itc.shared.FormatStringWriter;
import edu.gemini.itc.shared.Gaussian;
import edu.gemini.itc.shared.ObservationResult;
import edu.gemini.itc.parameters.ObservationDetailsParameters;
import edu.gemini.itc.parameters.TeleParameters;

public class PointSourceFractionCalculation implements SourceFractionCalculatable {

	/**
	 * @uml.property  name="im_qual"
	 */
	double im_qual=-1;
	/**
	 * @uml.property  name="ap_diam"
	 */
	double ap_diam;
	/**
	 * @uml.property  name="pixel_size"
	 */
	double pixel_size;
	/**
	 * @uml.property  name="ap_pix"
	 */
	double ap_pix;
	/**
	 * @uml.property  name="sw_ap"
	 */
	double sw_ap;
	/**
	 * @uml.property  name="npix"
	 */
	double Npix;
	/**
	 * @uml.property  name="source_fraction"
	 */
	double source_fraction;
	/**
	 * @uml.property  name="ap_type"
	 */
	String ap_type;
    /**
	 * @uml.property  name="sFprint"
	 */
    boolean SFprint=true; 
    
    double _ap_ratio;
    
    double _ap_frac;
        
	public PointSourceFractionCalculation(String ap_type, double ap_diam, double pixel_size) {
		this.ap_type = ap_type;
		this.ap_diam = ap_diam;
		this.pixel_size = pixel_size;
                
		}

	public void calculate() throws Exception {
		if (im_qual < 0)
			throw new Exception("Programming Error, Must set image quality before calling Calculate");

		if (ap_type.equals(ObservationDetailsParameters.AUTO_APER)) {
				ap_diam = 1.18 * im_qual;
      	} else if (ap_type.equals(
				ObservationDetailsParameters.USER_APER)) {
         		// Do nothing ap_diam is correct
      	} else {
      				throw new Exception(
      						"Unknown aperture type: " + ap_type);
      	}

		ap_pix = (Math.PI/4.) * (ap_diam/pixel_size) * (ap_diam/pixel_size);
      	Npix = (ap_pix >= 9) ? ap_pix : 9;
      	sw_ap = (ap_pix >= 9) ? ap_diam : 3.4 * pixel_size;

      	// Calculate the fraction of source flux contained in this aperture.
      	// Found by doing 2-d integral over assumed gaussian profile.
      	//double sigma = 2 * im_qual/2.355;
      	double sigma = im_qual/2.355;
      	double ap_ratio = 0.5 * (sw_ap/sigma);
		double ap_frac = Gaussian.get2DIntegralUF(ap_ratio);
		source_fraction = ((ap_ratio) > 5.0) ? 1.0 : ap_frac;
		_ap_ratio = ap_ratio;
		_ap_frac = ap_frac;
	}
	
	

	public String getTextResult(FormatStringWriter device, ObservationResult obsResult) {
		obsResult.setAperture(sw_ap);
		obsResult.setSourceFraction(source_fraction);
		obsResult.setNpix(Npix);
		
		StringBuffer sb = new StringBuffer();
		sb.append("software aperture diameter = " + device.toString(sw_ap) + " arcsec\n");
        if (SFprint) {
            sb.append("fraction of source flux in aperture = " + device.toString(source_fraction) + "\n");
        }
		sb.append("enclosed pixels = " + device.toString(Npix) + "\n");
		return sb.toString();
	}

	// must set image quality before calculate
	public void setImageQuality( double im_qual) {
		this.im_qual = im_qual;
	}
    
	public void setApType(String ap_type) { 
    	this.ap_type = ap_type; 
    }
    
    public void setApDiam(double ap_diam) { 
    	this.ap_diam = ap_diam; 
    }
    
    public void setSFPrint(boolean SFprint) { 
    	this.SFprint = SFprint; 
    }

	public double getSourceFraction() {
		return source_fraction;
	}
	
	public double getNPix() { 
		return Npix; 
	}
	
	public double getApDiam() { 
		return ap_diam; 
	}
	
	public double getApPix() { 
		return ap_pix; 
	}
	
	public double getSwAp() { 
		return sw_ap; 
	}
	
	public double getApRatio () {
		return _ap_ratio;
	}
	
	public double getApFrac () {
		return _ap_frac;
	}
}
