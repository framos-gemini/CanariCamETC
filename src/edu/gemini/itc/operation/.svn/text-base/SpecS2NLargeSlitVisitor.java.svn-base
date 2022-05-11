
package edu.gemini.itc.operation;

import edu.gemini.itc.shared.SampledSpectrumVisitor;
//import edu.gemini.itc.gmos.DetectorsTransmissionVisitor;
import edu.gemini.itc.shared.SampledSpectrum;
import edu.gemini.itc.shared.VisitableSampledSpectrum;
import edu.gemini.itc.shared.ArraySpectrum;
import edu.gemini.itc.shared.DefaultArraySpectrum;
import edu.gemini.itc.shared.ITCConstants;
import edu.gemini.itc.shared.WriteToFile;

/**
 * The SpecS2NLargeSlitVisitor is used to calculate the s2n of an observation using
 * a larger slit set.
 */
public class SpecS2NLargeSlitVisitor implements SampledSpectrumVisitor
{
    // private ArraySpectrum _telescopeBack = null;
    /**
	 * @uml.property  name="source_flux"
	 * @uml.associationEnd  
	 */
    private VisitableSampledSpectrum source_flux;
	/**
	 * @uml.property  name="background_flux"
	 * @uml.associationEnd  
	 */
	private VisitableSampledSpectrum background_flux;
	/**
	 * @uml.property  name="spec_noise"
	 * @uml.associationEnd  
	 */
	private VisitableSampledSpectrum spec_noise;
	/**
	 * @uml.property  name="spec_sourceless_noise"
	 * @uml.associationEnd  
	 */
	private VisitableSampledSpectrum spec_sourceless_noise;
	/**
	 * @uml.property  name="spec_signal"
	 * @uml.associationEnd  
	 */
	private VisitableSampledSpectrum spec_signal;
	
	private VisitableSampledSpectrum spectroPolimetry;
	
	/**
	 * @uml.property  name="spec_var_source"
	 * @uml.associationEnd  
	 */
	private VisitableSampledSpectrum spec_var_source;
	/**
	 * @uml.property  name="spec_var_background"
	 * @uml.associationEnd  
	 */
	private VisitableSampledSpectrum spec_var_background;
	/**
	 * @uml.property  name="sqrt_spec_var_background"
	 * @uml.associationEnd  
	 */
	private VisitableSampledSpectrum sqrt_spec_var_background;
	/**
	 * @uml.property  name="spec_exp_s2n"
	 * @uml.associationEnd  
	 */
	private VisitableSampledSpectrum spec_exp_s2n;
	/**
	 * @uml.property  name="spec_final_s2n"
	 * @uml.associationEnd  
	 */
	private VisitableSampledSpectrum spec_final_s2n;
    /**
	 * @uml.property  name="slit_width"
	 */
    private double slit_width;
	/**
	 * @uml.property  name="pixel_size"
	 */
	private double pixel_size;
	/**
	 * @uml.property  name="spec_source_fraction"
	 */
	private double spec_source_fraction;
	/**
	 * @uml.property  name="pix_width"
	 */
	private double pix_width;
	/**
	 * @uml.property  name="spec_Npix"
	 */
	private double spec_Npix;
	/**
	 * @uml.property  name="spec_frac_with_source"
	 */
	private double spec_frac_with_source;
	/**
	 * @uml.property  name="spec_exp_time"
	 */
	private double spec_exp_time;
	/**
	 * @uml.property  name="im_qual"
	 */
	private double im_qual;
	/**
	 * @uml.property  name="dark_current"
	 */
	private double dark_current;
	/**
	 * @uml.property  name="read_noise"
	 */
	private double read_noise;
	/**
	 * @uml.property  name="obs_wave"
	 */
	private double obs_wave;
	/**
	 * @uml.property  name="obs_wave_low"
	 */
	private double obs_wave_low;
	/**
	 * @uml.property  name="obs_wave_high"
	 */
	private double obs_wave_high;
	/**
	 * @uml.property  name="grating_res"
	 */
	private double grating_res;
	/**
	 * @uml.property  name="gratingDispersion_nm"
	 */
	private double gratingDispersion_nm;
	/**
	 * @uml.property  name="gratingDispersion_nmppix"
	 */
	private double gratingDispersion_nmppix;
	/**
	 * @uml.property  name="skyAper"
	 */
	private double skyAper;
    /**
	 * @uml.property  name="spec_number_exposures"
	 */
    private int spec_number_exposures;
	/**
	 * @uml.property  name="spectralBinning"
	 */
	private int spectralBinning;
    
    /**
	 * @uml.property  name="_dtv"
	 * @uml.associationEnd  
	 */
    private edu.gemini.itc.operation.DetectorsTransmissionVisitor _dtv;
   /**
    * Constructs SpecS2NVisitor with specified slit_width,
    * pixel_size, Smoothing Element, SlitThroughput, spec_Npix(sw aperture
    * size), ExpNum, frac_with_source, ExpTime .
    */
   public SpecS2NLargeSlitVisitor(double slit_width, double pixel_size,
   			 double pix_width, double obs_wave_low,
			 double obs_wave_high, double gratingDispersion_nm,
			 double gratingDispersion_nmppix, double grating_res,
			 double spec_source_fraction,double im_qual,
			 double spec_Npix, int spec_number_exposures,
			 double spec_frac_with_source, double spec_exp_time,
			 double dark_current, double read_noise ,
			 double skyAper, int spectralBinning)
       throws Exception
    {
	this.slit_width=slit_width;
        this.pixel_size=pixel_size;
	this.pix_width = pix_width;
	this.spec_source_fraction=spec_source_fraction;
	this.spec_Npix=spec_Npix;
	this.spec_frac_with_source=spec_frac_with_source;
	this.spec_exp_time=spec_exp_time;
	this.obs_wave_low=obs_wave_low;
	this.obs_wave_high = obs_wave_high;
	this.gratingDispersion_nm = gratingDispersion_nm;
	this.gratingDispersion_nmppix = gratingDispersion_nmppix;
	this.grating_res=grating_res;
	this.im_qual= im_qual;
	this.dark_current=dark_current;
	this.read_noise=read_noise;
	this.spec_number_exposures=spec_number_exposures;
	this.skyAper = skyAper;
	this.spectralBinning = spectralBinning;

   }

   /**
    * Implements the SampledSpectrumVisitor interface
    */
   public void visit(SampledSpectrum sed) throws Exception
   {
       //this.obs_wave = (obs_wave_low+obs_wave_high)/2;

	   

	   double width;
       //if image size is less than the slit width it will determine resolution
       if (im_qual<slit_width)
	   width = im_qual;
       else width = slit_width;
       //calc the width of a spectral resolution element in nm
       //double res_element = obs_wave/grism_res;
       // double res_element = gratingDispersion_nm*width/0.5; UF
       double res_element = gratingDispersion_nm*width/0.52;
       //and the data size in the spectral domain
       double res_element_data = res_element/source_flux.getSampling(); // /gratingDispersion_nmppix;
       //use the int value of spectral_pix as a smoothing element
       int smoothing_element = new Double(res_element_data+0.5).intValue();
       if (smoothing_element < 1) smoothing_element = 1;
	   ///////////////////////////////////////////////////////////////////////////////////////
	   //  We Don't know why but using just the smoothing element is not enough to create the resolution
	   //     that we expect.  Using a smoothing element of  =smoothing_element +1
	   //     May need to take this out in the future.
	   ///////////////////////////////////////////////////////////////////////////////////////
	   smoothing_element = smoothing_element +1;
       //System.out.println("Smoothing Element: " + smoothing_element+ " " + res_element_data + "res el" + res_element + " Sample : " + source_flux.getSampling());
       //System.out.println("gratingDispersion_nmppix: " + gratingDispersion_nmppix + "gratingDispersion_nm" + gratingDispersion_nm);
       // on the source and background
	   System.out.println(".................");
	   System.out.println("BeforeSourceFlux.dat");
	   WriteToFile.write("BeforeSourceFlux.dat", source_flux.getData());
       source_flux.smoothY(smoothing_element);
       background_flux.smoothY(smoothing_element);
       System.out.println("Afterspec_SourceFlux.dat");
       WriteToFile.write("Afterspec_SourceFlux.dat", source_flux.getData());
       
       // resample both sky and SED
	   SampledSpectrumVisitor source_resample = new ResampleWithPaddingVisitor(
	   					     obs_wave_low, obs_wave_high-1,
                                                     //source_flux.getStart(), source_flux.getEnd(), 
	   					     pix_width,0);
           SampledSpectrumVisitor background_resample = new ResampleWithPaddingVisitor(
	   					     obs_wave_low, obs_wave_high-1,
                                                     //source_flux.getStart(), source_flux.getEnd(), 
	   					     pix_width,0);
   	   source_flux.accept(source_resample);
	   System.out.println("resample_sourceFlux.dat");
	   WriteToFile.write("resample_sourceFlux.dat", source_flux.getData());
	   background_flux.accept(background_resample);

       //Apply the detectorts transmission.  This will create the detector gaps
       	 //DetectorsTransmissionVisitor dtv =
	   //	 			new DetectorsTransmissionVisitor(spectralBinning);

	   	 source_flux.accept(_dtv);
	   	System.out.println("DTVsourceFlux.dat");
		   WriteToFile.write("resample sourceFlux.dat", source_flux.getData());
	   	 background_flux.accept(_dtv);

       // the number of exposures measuring the source flux is
       double spec_number_source_exposures =
	   spec_number_exposures*spec_frac_with_source;

       spec_var_source = (VisitableSampledSpectrum)source_flux.clone();
       spec_var_background = (VisitableSampledSpectrum)background_flux.clone();
       //Shot noise on the source flux in aperture
       for(int i = 0; i<source_flux.getLength();++i)
    	   spec_var_source.setY(i,source_flux.getY(i)*spec_source_fraction*spec_exp_time*gratingDispersion_nmppix);

       //Shot noise on background flux in aperture
       for(int i = 0; i<spec_var_background.getLength();++i)
	   //spec_var_background.setY(i,background_flux.getY(i)*slit_width* pixel_size * spec_Npix * spec_exp_time*gratingDispersion_nmppix); UF
    	   spec_var_background.setY(i,background_flux.getY(i)* pixel_size * pixel_size * spec_Npix * spec_exp_time*gratingDispersion_nmppix);

       //Shot noise on dark current flux in aperture
       double spec_var_dark = dark_current*spec_Npix*spec_exp_time;

       //Readout noise in aperture
       double spec_var_readout = read_noise*read_noise*spec_Npix;
       //Create a container for the total and sourceless noise in the
       //aperture
       spec_noise = (VisitableSampledSpectrum)source_flux.clone();
       spec_sourceless_noise = (VisitableSampledSpectrum)source_flux.clone();

       spec_signal = (VisitableSampledSpectrum)source_flux.clone();
       spec_exp_s2n = (VisitableSampledSpectrum)source_flux.clone();
       spec_final_s2n = (VisitableSampledSpectrum)source_flux.clone();
       sqrt_spec_var_background =
	   (VisitableSampledSpectrum)spec_var_background.clone();

       // Total noise in the aperture is ...
       for(int i = 0; i<spec_noise.getLength();++i)
	   spec_noise.setY(i,Math.sqrt(spec_var_source.getY(i)+ spec_var_background.getY(i)+ spec_var_dark+spec_var_readout));

       //System.out.println("spec_var_dark(dark[" + dark_current + "]*NumberofSpecPix["+ spec_Npix+"]*spec_exp_time["+spec_exp_time+"])"+ spec_var_dark+ "spec_var_readout"+ spec_var_readout);
       // and ...
       for(int i = 0; i<spec_sourceless_noise.getLength();++i)
    	   spec_sourceless_noise.setY(i,Math.sqrt(spec_var_background.getY(i)+ spec_var_dark+spec_var_readout));

       //total source flux in the aperture
       for(int i = 0; i<spec_signal.getLength();++i)
    	   spec_signal.setY(i,source_flux.getY(i) * spec_source_fraction*spec_exp_time*gratingDispersion_nmppix);

       //S2N for one exposure
       for(int i = 0; i<spec_exp_s2n.getLength();++i)
    	   spec_exp_s2n.setY(i,spec_signal.getY(i)/spec_noise.getY(i));

       double noiseFactor = 1+(1/skyAper);
       //S2N for the observation
       for(int i = 0; i<spec_final_s2n.getLength();++i)
    	   spec_final_s2n.setY(i,Math.sqrt(spec_number_source_exposures)*spec_signal.getY(i) /
			       			Math.sqrt(spec_signal.getY(i)+noiseFactor * spec_sourceless_noise.getY(i) * spec_sourceless_noise.getY(i)));

       //Finally create the Sqrt(Background) sed for plotting
      	for(int i = 0; i<spec_var_background.getLength();++i)
      		sqrt_spec_var_background.setY(i,Math.sqrt(spec_var_background.getY(i)));


   }

    public void setSourceSpectrum(VisitableSampledSpectrum sed)
    {
	source_flux = sed;
    }

    public void setBackgroundSpectrum(VisitableSampledSpectrum sed)
    {
	background_flux = sed;
    }
    
    public void setDetectorTransmission(edu.gemini.itc.operation.DetectorsTransmissionVisitor dtv) 
    {
        _dtv = dtv;
    }
    
    /**
	 * @param gratingDispersion_nmppix
	 * @uml.property  name="gratingDispersion_nmppix"
	 */
    public void setGratingDispersion_nmppix(double gratingDispersion_nmppix)
    {
        this.gratingDispersion_nmppix=gratingDispersion_nmppix;
    }

    /**
	 * @param gratingDispersion_nm
	 * @uml.property  name="gratingDispersion_nm"
	 */
    public void setGratingDispersion_nm(double gratingDispersion_nm)
    {
        this.gratingDispersion_nm=gratingDispersion_nm;
    }
    public void setSpectralPixelWidth(double pix_width)
    {
        this.pix_width = pix_width;
    }
    
    public void setStartWavelength(double obs_wave_low) {
        this.obs_wave_low=obs_wave_low;
    }
    
    public void setEndWavelength(double obs_wave_high) {
        this.obs_wave_high=obs_wave_high;
    }
    
    public VisitableSampledSpectrum getSignalSpectrum()
    {
	return spec_signal;
    }

    public VisitableSampledSpectrum getBackgroundSpectrum()
    {
    	return sqrt_spec_var_background ;
    }

    public VisitableSampledSpectrum getExpS2NSpectrum()
    {
    	return spec_exp_s2n;
    }

    public VisitableSampledSpectrum getFinalS2NSpectrum()
    {
    	return spec_final_s2n;
    }

   public String toString()
   {
      return "SpecS2NVisitor ";
   }

public VisitableSampledSpectrum getSpectroPolimetry() {
	return spectroPolimetry;
}

public void setSpectroPolimetry(VisitableSampledSpectrum spectroPolimetry) {
	this.spectroPolimetry = spectroPolimetry;
}
}
