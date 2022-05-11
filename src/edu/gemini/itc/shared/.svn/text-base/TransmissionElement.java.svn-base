// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: TransmissionElement.java,v 1.4 2004/02/13 13:00:59 bwalls Exp $
//
package edu.gemini.itc.shared;
import java.io.File;
import java.io.IOException;

/**
 * A TransmissionElement has a transmission spectrum that can
 * be convolved with a spectrum.
 * Examples are water in the atmosphere and instrument filters.
 * This class can be used directly if the client knows the path
 * to the data file.  But usually a class will be derived from this
 * class that knows something about the data file naming conventions
 * for that type of element.
 */
public class TransmissionElement implements SampledSpectrumVisitor {
    /**
	 * @uml.property  name="_resourceName"
	 */
    private String _resourceName = null;

    // The transmission spectrum
    /**
	 * @uml.property  name="_trans"
	 * @uml.associationEnd  
	 */
    private ArraySpectrum _trans = null;

    /**
     * Default constructor for TransmissionElement.
     * Class will not be useful until transmission spectrum is set.
     */
    public TransmissionElement() {
    }

    /**
     * Constructs a TransmissionElement
     * @param name name of this component
     * @param transmission transmission spectrum for this component
     */
    public TransmissionElement(ArraySpectrum transmission) {
        _trans = (ArraySpectrum) transmission.clone();
    }

    /**
     * Constructs a TransmissionElement using specified transmission data file
     * @param name name of this component
     * @param transmissionFileName transmission spectrum for this component
     */
    public TransmissionElement(String resourceName) throws Exception {
        _resourceName = resourceName;
        setTransmissionSpectrum(resourceName);
    }

    /**
     * Attempts to load the specified transmision spectrum.
     */
    public void setTransmissionSpectrum(String resourceName) throws Exception {
        _resourceName = resourceName;
        _trans = new DefaultArraySpectrum(resourceName);
    }

    /**
     * Set the transmission spectrum.
     */
    public void setTransmissionSpectrum(ArraySpectrum spectrum) {
        _trans = spectrum;
    }

    /**
     * Apply the transmission convolution for this component.
     */
    public void visit(SampledSpectrum sed) {
    	System.out.println("Convolucion. "+ _resourceName);
    	String aux = _resourceName.replace('/', '_');
    	File f = new File("/tmp/"+aux+".dat");
    	String suffix = ".dat";
    	if (f.exists()) {
    		suffix = "2.dat";
    	}
    	try {
			WriteToFile.write("sed"+suffix, sed.getData());	
			WriteToFile.write(aux + suffix, _trans.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        double multiplier = 0;
        
        for (int i = 0; i < sed.getLength(); i++) {
            double startval = sed.getX(i);
            multiplier = _trans.getY(startval);
            sed.setY(i, sed.getY(i) * multiplier);
        }
        
        try {
			WriteToFile.write("Convulution_"+ aux + suffix, sed.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }

    public String toString() {
        if (_resourceName == null) {
            return "Transmission Element";
        } else {
            return "Transmission Element - data file " + _resourceName;
        }
    }
}
