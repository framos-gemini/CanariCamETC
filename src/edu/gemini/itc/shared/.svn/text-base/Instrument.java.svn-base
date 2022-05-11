// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: Instrument.java,v 1.7 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The Instrument class is the class that any instrument should extend. It defines the common properties of any given Instrumnet. The important piece of data is the _list. This is a linked list that contains all of the Components that make up the instrument.
 */
public abstract class Instrument {
    public static final String DATA_SUFFIX = ITCConstants.DATA_SUFFIX;

    /**
	 * @uml.property  name="_name"
	 */
    private String _name;

    // The range and sampling allowed by this instrument.
    /**
	 * @uml.property  name="_sub_start"
	 */
    private double _sub_start;
    /**
	 * @uml.property  name="_sub_end"
	 */
    private double _sub_end;
    /**
	 * @uml.property  name="_sampling"
	 */
    private double _sampling;

    // List of Components
    /**
	 * @uml.property  name="_components"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="edu.gemini.itc.shared.TransmissionElement"
	 */
    private List _components;

    // Each Instrument adds its own background.
    /**
	 * @uml.property  name="_background"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private ArraySpectrum _background;

    /**
	 * @uml.property  name="_background_file_name"
	 */
    private String _background_file_name;

    // Transformation between arcsec and pixels on detector
    /**
	 * @uml.property  name="_plate_scale"
	 */
    private double _plate_scale;
    // Binning is not a fixed property, can choose both x and y binning.
    // Doesn't yet support different binning in x,y.
    // Leave placeholder for y binning, but will just use x for now.
    //private int                _xBinning = 1;
    //private int                _yBinning = 1;

    /**
	 * @uml.property  name="_pixel_size"
	 */
    private double _pixel_size;  // _plate_scale * binning

    // read noise is independent of binning
    /**
	 * @uml.property  name="_read_noise"
	 */
    private double _read_noise;  // electrons/pixel
    
   /**
	 * @uml.property  name="_dc_per_pixel"
	 */
    private double _dc_per_pixel; // electrons/s/pixel
    /**
	 * @uml.property  name="_dark_current"
	 */
    private double _dark_current;

    // analog to digital unit, conversion between electrons and counts.
    // not used yet, but here so we don't forget.
    /**
	 * @uml.property  name="_adu"
	 */
    private double _adu = 1.0;
    
    private TextFileReader _textFile = null;

    protected Instrument(String name, double sub_start, double sub_end,
                         double sampling, String background_file,
                         double plate_scale,
                         double read_noise, int dc_per_pixel) throws Exception {
        _name = name;
        _sub_start = sub_start;
        _sub_end = sub_end;
        _sampling = sampling;
        _components = new LinkedList();

        _background = new DefaultArraySpectrum(background_file + ITCConstants.DATA_SUFFIX);

        _plate_scale = plate_scale;
        _pixel_size = _plate_scale;//*getXBinning();
        _read_noise = read_noise;
        _dc_per_pixel = dc_per_pixel;
        _dark_current = _dc_per_pixel;//*getXBinning();
    }

    /**
     * All instruments have data files of the same format.
     * Note that one instrument accesses two files.
     * One gives instrument info, the other has transmission curve.
     * @param subdir The subdirectory under lib where files are located
     * @param filename The filename of the instrument data file
     */
    // Automatically loads the background data.
    protected Instrument(String subdir, String filename) throws Exception {
        String dir = ITCConstants.LIB + "/" + subdir + "/";
        _textFile = new TextFileReader(dir + filename);
        _components = new LinkedList();
        _name = _textFile.readLine();
        _sub_start = _textFile.readDouble();
        _sub_end = _textFile.readDouble();
        _sampling = _textFile.readDouble();
        _background_file_name = _textFile.readString();
        _background = new DefaultArraySpectrum(dir + _background_file_name);
        _plate_scale = _textFile.readDouble();
        _pixel_size = _plate_scale;
        _dc_per_pixel = _textFile.readDouble();
        _dark_current = _dc_per_pixel;
        _read_noise = _textFile.readDouble();
    }

    /**
     * Method adds the instrument background flux to the specified spectrum.
     */
    public void addBackground(ArraySpectrum sky) {
        double val = 0;
        for (int i = 0; i < sky.getLength(); i++) {
            val = _background.getY(sky.getX(i)) + sky.getY(i);
            sky.setY(i, val);
        }

    }

    /**
     * Method to iterate through the Components list and apply the
     * accept method of each component to a sed.
     */
    public void convolveComponents(VisitableSampledSpectrum sed)
            throws Exception {
        for (Iterator itr = _components.iterator(); itr.hasNext();) {
            TransmissionElement te = (TransmissionElement) itr.next();
            
            sed.accept((TransmissionElement) te);
        }

    }

    protected void addComponent(TransmissionElement c) {
        _components.add(c);
        //System.out.println("added Component: " + c.toString());

    }
    
    public TextFileReader getTextFile () {
    	return _textFile;
    }

    // Accessor methods
    public String getName() {
        return _name;
    }

    public double getStart() {
        return _sub_start;
    }

    public double getEnd() {
        return _sub_end;
    }

    public abstract double getObservingStart();

    public abstract double getObservingEnd();

    public double getSampling() {
        return _sampling;
    }

    public double getPixelSize() {
        return _pixel_size;
    }

    public double getReadNoise() {
        return _read_noise;
    }

    public double getDarkCurrent() {
        return _dark_current;
    }
    
    public double setDarkCurrent(double darkCurrent) {
        return _dark_current = darkCurrent;
    }


    protected void resetBackGround(String subdir, String filename_prefix) throws Exception {
        String dir = ITCConstants.LIB + "/" + subdir + "/";

        _background = new DefaultArraySpectrum(dir + filename_prefix + _background_file_name);
    }

    /**
     * Returns the effective observing wavelength.
     * This is properly calculated as a flux-weighted averate of
     * observed spectrum.  So this may be temporary.
     * @return Effective wavelength in nm
     */
    public abstract int getEffectiveWavelength();

    /**
	 * Returns the subdirectory where this instrument's data files are.
	 * @uml.property  name="directory"
	 */
    public abstract String getDirectory();

    /** The suffix on instrument data files. */
    public static String getSuffix() {
        return DATA_SUFFIX;
    }

    public String opticalComponentsToString() {
        String s = "Optical Components: <BR>";
        for (Iterator itr = _components.iterator(); itr.hasNext();) {
            s += "<LI>" + itr.next().toString() + "<BR>";
        }
        return s;
    }
    
    public List<String> getComponentsToString() {
        List<String> listcomponents = new ArrayList<String>();
    	for (Iterator itr = _components.iterator(); itr.hasNext();) {
            listcomponents.add(itr.next().toString());
        }
        return listcomponents;
    }
    

    public String toString() {
        String s = "Instrument configuration: \n";
        s += "Optical Components: <BR>";
        for (Iterator itr = _components.iterator(); itr.hasNext();) {
            s += "<LI>" + itr.next().toString() + "<BR>";
        }
        s += "<BR>";
        s += "Pixel Size: " + getPixelSize() + "<BR>" + "<BR>";

        return s;
    }

	
}


