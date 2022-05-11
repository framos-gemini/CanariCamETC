package edu.gemini.itc.operation;

import edu.gemini.itc.shared.FormatStringWriter;
import edu.gemini.itc.shared.ObservationResult;

public class ImagingUSBS2NMethodBCalculation extends ImagingS2NCalculation {

    /**
	 * @uml.property  name="number_exposures"
	 */
    int number_exposures;
	/**
	 * @uml.property  name="int_req_source_exposures"
	 */
	int int_req_source_exposures;
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
	/**
	 * @uml.property  name="req_s2n"
	 */
	double req_s2n;
	/**
	 * @uml.property  name="req_source_exposures"
	 */
	double req_source_exposures;
	/**
	 * @uml.property  name="req_number_exposures"
	 */
	double req_number_exposures;
	/**
	 * @uml.property  name="effective_s2n"
	 */
	double effective_s2n;

    public ImagingUSBS2NMethodBCalculation(int number_exposures,
                                           double frac_with_source,
                                           double exposure_time, double read_noise,
                                           double req_s2n,
                                           double pixel_size) {

        this.number_exposures = number_exposures;
        this.frac_with_source = frac_with_source;
        this.exposure_time = exposure_time;
        this.read_noise = read_noise;
        this.pixel_size = pixel_size;
        // Because this is USB the requested s2n is diff by...
        this.req_s2n = req_s2n;//Math.sqrt(1/(pixel_size*pixel_size));

    }

    public void calculate() throws Exception {
        super.calculate();
        req_source_exposures = (req_s2n / signal) * (req_s2n / signal) *
                (signal + noiseFactor * sourceless_noise * sourceless_noise);

        int_req_source_exposures =
                new Double(Math.ceil(req_source_exposures)).intValue();

        req_number_exposures =
                int_req_source_exposures / frac_with_source;

        effective_s2n =
                ((Math.sqrt(int_req_source_exposures) * signal) /
                Math.sqrt(signal + noiseFactor * sourceless_noise * sourceless_noise));
//*Math.sqrt(1/(pixel_size*pixel_size));


    }

    public String getTextResult(FormatStringWriter device, ObservationResult obsResult) {
        StringBuffer sb = new StringBuffer(super.getTextResult(device, obsResult));
        device.setPrecision(2);
        device.clear();
        obsResult.setNumberOfExposures(req_number_exposures);
        obsResult.setNumExposureFracSource(req_number_exposures * frac_with_source);
        obsResult.setMethod('B');
        sb.append("Derived number of exposures = " + device.toString(req_number_exposures) +
                  " , of which " + device.toString(req_number_exposures * frac_with_source));

        if (req_number_exposures == 1)
            sb.append(" is on source.\n");
        else
            sb.append(" are on source.\n");

        obsResult.setEffectiveS2N(effective_s2n);
        
        sb.append("The effective S/N for the whole observation is "
		  + device.toString(effective_s2n) + " (including sky subtraction)\n\n");

        double totExpTime = req_number_exposures * exposure_time;

        obsResult.setTotalIntegrationTime(totExpTime);
        
        sb.append("Required total integration time = " + device.toString( totExpTime ) + " secs,    ( = "
		  + device.toString( totExpTime/60 ) + " mins,   = "
		  + device.toString( totExpTime/60/60 ) + " hours)\n\n");

        double srcExpTime = exposure_time * req_number_exposures * frac_with_source;
        obsResult.setSouceExpTime(srcExpTime);
        
        sb.append("Required source integration time =  " + device.toString( srcExpTime )
		  + " secs,   ( = " + device.toString( srcExpTime/60 ) + " mins,   = "
		  + device.toString( srcExpTime/60/60 ) + " hours)\n");

        return sb.toString();
    }

	

}

