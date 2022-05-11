// Copyright 2001 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
package edu.gemini.itc.shared;

/**
 * The USBMorphology concreate class implements the operations that all
 * that are defined in it's abstract parent class Morphology2d.
 *
 */
public class USBMorphology extends Morphology3D {


    /**
     * We should provide methods that allow the calculation of
     * integrals for square, circular.
     */


    public double get2DSquareIntegral(double xMin, double xMax, double yMin, double yMax) {
        return 1;
    }


    public void accept(MorphologyVisitor v) throws Exception {
        v.visitUSB(this);
    }


}
