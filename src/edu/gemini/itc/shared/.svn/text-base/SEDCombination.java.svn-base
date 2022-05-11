// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
package edu.gemini.itc.shared;

import java.util.HashMap;

/**
 * This class allows basic combinations of multiple sed's.
 */
public final class SEDCombination {
    public static final int ADD = 0;
    public static final int SUBTRACT = 1;
    public static final int DIVIDE = 2;
    public static final int MULTIPLY = 4;


    public static VisitableSampledSpectrum combine(VisitableSampledSpectrum sed1, VisitableSampledSpectrum sed2, int operation) throws Exception {

        if (sed1.getSampling() != sed2.getSampling())
            throw new Exception("Sampling of sed1 must equal sampling of sed2");

        switch (operation) {
            case ADD:
                return add(sed1, sed2);
                //case SUBTRACT:
                //return subtract(sed1,sed2);
                //case DIVIDE:
                //return divide(sed1,sed2);
                //case MULTIPLY:
                //return multiply(sed1,sed2);
            default:
                throw new Exception("No such operation" + operation);
        }
    }

    public static VisitableSampledSpectrum add(VisitableSampledSpectrum sed1, VisitableSampledSpectrum sed2) {
        double Xstart = Xmin(sed1, sed2);
        double Xend = Xmax(sed1, sed2);

        double sampling = sed1.getSampling();

        //double data1[][] = new double[2][sed1.getLength()];
        //double data2[][] = new double[2][sed2.getLength()];

        double[] flux = new double[new Double((Xend - Xstart) / sampling).intValue() + 5];

        int j = 0;
        for (double i = Xstart; i < Xend; i += sampling) {
            flux[j++] = sed1.getY(i) + sed2.getY(i);
        }

        SEDFactory fac = new SEDFactory();
        return (VisitableSampledSpectrum) fac.getSED(flux, Xstart, sampling);

    }

    private static double Xmin(VisitableSampledSpectrum sed1, VisitableSampledSpectrum sed2) {
        if (sed1.getStart() < sed2.getStart())
            return sed1.getStart();
        else
            return sed2.getStart();
    }

    private static double Xmax(VisitableSampledSpectrum sed1, VisitableSampledSpectrum sed2) {
        if (sed1.getEnd() > sed2.getEnd())
            return sed1.getEnd();
        else
            return sed2.getEnd();
    }


}












