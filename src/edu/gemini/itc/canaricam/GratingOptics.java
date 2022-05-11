// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
package edu.gemini.itc.canaricam;

import edu.gemini.itc.shared.TransmissionElement;
import edu.gemini.itc.shared.Instrument;
import edu.gemini.itc.shared.TextFileReader;
import edu.gemini.itc.shared.ITCConstants;

import java.util.List;
import java.util.ArrayList;

import java.text.ParseException;

import java.io.IOException;

/**
 * This represents the transmission and properties of the Grating optics.
 */
public class GratingOptics extends TransmissionElement {
    
    
    /**
	 * @uml.property  name="_x_values"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Double"
	 */
    private List _x_values;
    /**
	 * @uml.property  name="_resolvingPowerArray"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Integer"
	 */
    private List _resolvingPowerArray;
    /**
	 * @uml.property  name="_dispersionArray"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Double"
	 */
    private List _dispersionArray;
    /**
	 * @uml.property  name="_blazeArray"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Integer"
	 */
    private List _blazeArray;
    /**
	 * @uml.property  name="_resolutionArray"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Double"
	 */
    private List _resolutionArray;
    /**
	 * @uml.property  name="_gratingNameArray"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private List _gratingNameArray;
    /**
	 * @uml.property  name="_gratingName"
	 */
    private String _gratingName;
    /**
	 * @uml.property  name="_centralWavelength"
	 */
    private double _centralWavelength;
    /**
	 * @uml.property  name="_detectorPixels"
	 */
    private int _detectorPixels;
    /**
	 * @uml.property  name="_spectralBinning"
	 */
    private int _spectralBinning;
    
    public GratingOptics(String directory, String gratingName,
    String stringSlitWidth,
    double centralWavelength,
    int detectorPixels,
    int spectralBinning)
    throws Exception {
        
        super(directory + CanariCam.getPrefix() +
        gratingName + Instrument.getSuffix());
        
        _spectralBinning = spectralBinning;
        
        _detectorPixels = detectorPixels;
        _centralWavelength = centralWavelength;
        _gratingName=gratingName;
        
        //Read The transmission file for the start and stop wavelengths
        TextFileReader dfr = new TextFileReader(directory +
        CanariCam.getPrefix() +
        gratingName +
        Instrument.getSuffix());
        _x_values = new ArrayList();
        
        double x=0;
        double y=0;
        
        try {
            while (true) {
                x= dfr.readDouble();
                _x_values.add(new Double(x));
                y= dfr.readDouble();
            }
        } catch (ParseException e) {
            throw e;
        } catch (IOException e) {
            // normal eof
        }
        
        //New read of Grating Proporties
        TextFileReader grismProperties = new TextFileReader(directory+
        CanariCam.getPrefix()+
        "gratings"+
        Instrument.getSuffix());
        _resolvingPowerArray = new ArrayList();
        _gratingNameArray = new ArrayList();
        _blazeArray = new ArrayList();
        _resolutionArray = new ArrayList();
        _dispersionArray = new ArrayList();
        try {
            while (true) {
                _gratingNameArray.add(new String(grismProperties.readString()));
                _blazeArray.add(new Integer(grismProperties.readInt()));
                _resolvingPowerArray.add(new Integer(grismProperties.readInt()));
                _resolutionArray.add(new Double(grismProperties.readDouble()));
                _dispersionArray.add(new Double(grismProperties.readDouble()));
            }
        } catch (ParseException e) {
            throw e;
        } catch (IOException e) {
            // normal eof
        }
        
    }
    
    
    public double getStart() {
        return _centralWavelength -(
        (((Double)_dispersionArray.get(getGratingNumber())).doubleValue())
        *_detectorPixels/2)*_spectralBinning;
    }
    
    public double getEnd() {
        return _centralWavelength +(
        (((Double)_dispersionArray.get(getGratingNumber())).doubleValue())
        *_detectorPixels/2)*_spectralBinning;
    }
    
    public double getEffectiveWavelength() {
        return _centralWavelength;
    }
    
    public double getPixelWidth() {
        return ((Double)_dispersionArray.get(getGratingNumber())).doubleValue()*_spectralBinning;
        
    }
    
    public int getGratingNumber() {
        int grating_num=0;
        
        if (_gratingName.equals(CanariCamParameters.LORES10_G5401)){
            grating_num= CanariCamParameters.LORES10;
        } else if (_gratingName.equals(CanariCamParameters.LORES20_G5402)){
            grating_num= CanariCamParameters.LORES20;
        } else if (_gratingName.equals(CanariCamParameters.HIRES10_G5403)){
            grating_num= CanariCamParameters.HIRES10;
        }
        return grating_num;
    }
    
    public double getGratingResolution(){
        return ((Integer)_resolvingPowerArray.get(getGratingNumber())).intValue();
    }
    
    public String getGratingName() {
        return (String)_gratingNameArray.get(getGratingNumber());
    }
    
    public double getGratingBlaze() {
        return ((Integer)_blazeArray.get(getGratingNumber())).intValue();
    }
    
    public double getGratingDispersion_nm() {
        return ((Double)_resolutionArray.get(getGratingNumber())).doubleValue();
    }
    
    public double getGratingDispersion_nmppix() {
        return ((Double)_dispersionArray.get(getGratingNumber())).doubleValue() *_spectralBinning;
    }
    
    
    public String toString() {
        return "Grating Optics: " + _gratingName;}
    
}
