// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
//
package edu.gemini.itc.shared;

import edu.gemini.itc.shared.TextFileReader;
import edu.gemini.itc.shared.ITCConstants;
import edu.gemini.itc.shared.DefaultArraySpectrum;


import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import java.text.ParseException;

import java.io.IOException;

/**
 * ErrorFunction
 * This class exists so that the client can calculate a value
 * from the Error function given a z.
 */
public class ErrorFunction {

    private static final String DEFAULT_ERROR_FILENAME = ITCConstants.CALC_LIB + "/"
            + "Error_Function" + ITCConstants.DATA_SUFFIX;
    private static final double MAX_Z_VALUE = 2.9;
    private static final double MIN_Z_VALUE = -3.0;

    /**
	 * @uml.property  name="_Filename"
	 */
    private String _Filename;
    /**
	 * @uml.property  name="_x_values"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Double"
	 */
    private List _x_values;
	/**
	 * @uml.property  name="_y_values"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Double"
	 */
	private List _y_values;
    /**
	 * @uml.property  name="errorFunction"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private DefaultArraySpectrum errorFunction;


    public ErrorFunction() throws Exception {
        this(ErrorFunction.DEFAULT_ERROR_FILENAME);
    }

    public ErrorFunction(String Filename) throws Exception {

        _Filename = Filename;

        TextFileReader dfr = new TextFileReader(_Filename);
        _x_values = new ArrayList();
        _y_values = new ArrayList();

        double x = 0;
        double y = 0;

        try {

            while (true) {
                x = dfr.readDouble();
                _x_values.add(new Double(x));
                y = dfr.readDouble();
                _y_values.add(new Double(y));
            }
        } catch (ParseException e) {
            throw e;
        } catch (IOException e) {
            // normal eof
        }
        //Create a new DefaultArraySpectrum.  This already has interpolation
        //built in.
        errorFunction = new DefaultArraySpectrum(_x_values, _y_values);

    }

    public double getERF(double z) {
        if (z > ErrorFunction.MAX_Z_VALUE)
            return 1;
        else if (z <= ErrorFunction.MIN_Z_VALUE)
            return -1;
        else
            return errorFunction.getY(z);

    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ErrorFuntion:\n");
        Iterator xIter = _x_values.iterator();

        Iterator yIter = _y_values.iterator();

        while (xIter.hasNext() && yIter.hasNext())
            sb.append(xIter.next().toString() + "\t" + yIter.next().toString() + "\n");

        return sb.toString();
    }


}
