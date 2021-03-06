// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: PowerLawSpectrum.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

import edu.gemini.itc.parameters.SourceDefinitionParameters;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.text.ParseException;

/**
 * This class creates a PowerLaw spectrum over the interval defined by the
 * blocking filter.  The code comes from PPuxley's Mathcad Demo
 * This Class implements Visitable sampled specturm to create the sed.
 */

public class PowerLawSpectrum extends DefaultSampledSpectrum {

    public PowerLawSpectrum(double powerLawIndex, double start, double end,
                            double interval, double z) {
        super(new double[1], 0.0, 1.0);  // create a dummy Default Spectrum.  Will be
        //  will be overwritten later.
        double _flux;

        double _sampling = interval;
        double lambda;  // var to be used to construct sed

        double _start = 0.2 * start;//.8
        double _end = 1.8 * end;//1.2
        //shift start and end depending on redshift
        _start /= (1 + z);
        _end /= (1 + z);

        int n = (int) ((_end - _start) / _sampling + 1);
        double[] fluxArray = new double[n];
//System.out.println("Array: " + (n+40) + " sample "+ _sampling);


        int i = 0;

        for (double lam = _start; lam <= _end; lam += _sampling) {
            fluxArray[i] = _pLawFlux(lam, powerLawIndex);
            i++;

        }

        reset(fluxArray, _start, _sampling);

        //_spectrum.print();

    }


    private double _pLawFlux(double lambda, double powerLawIndex) {
        //this funtion will calculate the eline spectum for a given wavelen
        // and sigme (specified by the user. The flux is just the line flux
        // of the object in question.  The units are returned internal units.
        // That is good so we dont have to do any thing to the result.

        double returnFlux;

        returnFlux = Math.pow(lambda, powerLawIndex);
        return returnFlux;
    }

}
