package edu.gemini.itc.operation;

import edu.gemini.itc.shared.FormatStringWriter;
import edu.gemini.itc.shared.ObservationResult;

public class ImagingS2NMethodACalculation extends ImagingS2NCalculation {

    /**
	 * @uml.property  name="number_exposures"
	 */
    int number_exposures;
    /**
	 * @uml.property  name="frac_with_source"
	 */
    double frac_with_source;
	/**
	 * @uml.property  name="exp_s2n"
	 */
	double exp_s2n;
	/**
	 * @uml.property  name="final_s2n"
	 */
	double final_s2n;
	/**
	 * @uml.property  name="number_source_exposures"
	 */
	double number_source_exposures;

    public ImagingS2NMethodACalculation(int number_exposures,
                                        double frac_with_source,
                                        double exposure_time, double read_noise,
                                        double pixel_size)
    {
        this.number_exposures = number_exposures;
        this.frac_with_source = frac_with_source;
        this.exposure_time = exposure_time;
        this.read_noise = read_noise;
        this.pixel_size = pixel_size;
    }

    public void calculate() throws Exception {
        super.calculate();
        number_source_exposures = number_exposures * frac_with_source;

        if (number_source_exposures != (int) number_source_exposures) {
            throw new Exception("Fraction with source value " +
                                "produces non-integral number of source exposures " +
                                "with source.");
        }

        exp_s2n = signal / noise;

        final_s2n = Math.sqrt(number_source_exposures) * signal /
	    Math.sqrt( signal + noiseFactor * sourceless_noise * sourceless_noise );
    }

    public double finalSNR() { return final_s2n; }
    
    public double getExpS2N() {return exp_s2n;  }

    public String getTextResult(FormatStringWriter device, ObservationResult obsResult) {
        StringBuffer sb = new StringBuffer(super.getTextResult(device, obsResult));
        
        obsResult.setIntermediateS2N(exp_s2n);
        obsResult.setFinalS2N(final_s2n);
        obsResult.setMethod('A');
        
        sb.append("Intermediate S/N for one exposure = " + device.toString(exp_s2n) + "\n\n");

        sb.append("resulting S/N for the whole observation = " + device.toString(final_s2n) +
                  " (including sky subtraction)\n\n");

		double totExpTime = exposure_time * number_exposures;
		obsResult.setTotalIntegrationTime(totExpTime);
		
		sb.append("Requested total integration time = " + device.toString( totExpTime ) + " secs,    ( = "
			  + device.toString( totExpTime/60 ) + " mins,   = "
			  + device.toString( totExpTime/60/60 ) + " hours)\n\n");
	
		double srcExpTime = exposure_time * number_exposures * frac_with_source;
		obsResult.setSouceExpTime(srcExpTime);
		
		sb.append("_____On source integration time =  " + device.toString( srcExpTime )
			  + " secs,   ( = " + device.toString( srcExpTime/60 ) + " mins,   = "
			  + device.toString( srcExpTime/60/60 ) + " hours)\n");

        return sb.toString();
    }
}

