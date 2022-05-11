// Copyright 2001 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
package edu.gemini.itc.shared;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * This is the concrete class that plays the role of Composite in the
 * composite pattern from GoF book.  This allows an aperture or a group of
 * apertures to be treated the same by the program.  This will be helpful
 * when we implement multiple IFU's.
 * The class also plays the role of Visitor to a morphology.  This allows
 * the class to calculate different values of the SourceFraction for different
 * types of Morphologies.
 */
public class ApertureComposite extends ApertureComponent {
    /**
	 * @uml.property  name="apertureList"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="edu.gemini.itc.shared.ApertureComponent"
	 */
    List apertureList = new ArrayList();

    //Methods for creating and removing apertures
    public void addAperture(ApertureComponent ap) {
        apertureList.add(ap);
    }

    public void removeAperture(int x) {
        if (x < apertureList.size() && x >= 0)
            apertureList.remove(x);
    }

    public void removeAllApertures() {
        apertureList.clear();
    }

    public ApertureComponent getAperture(int x) {
        if (x < apertureList.size() && x >= 0)
            return (ApertureComponent) apertureList.get(x);
        else
            return null;
    }

    private int getNumberOfApertures() {
        return apertureList.size();
    }

    //Methods for visiting with a group of apertures
    public void visitGaussian(Morphology3D morphology) {
        ListIterator apIter = apertureList.listIterator();
        while (apIter.hasNext()) {
            try {
                ((ApertureComponent) apIter.next()).visitGaussian(morphology);
            } catch (Exception e) {
                System.out.println(" Could Not visit Gaussian Morpology with an aperture.");
            }

        }
    }

    public void visitUSB(Morphology3D morphology) {
        ListIterator apIter = apertureList.listIterator();
        while (apIter.hasNext()) {
            try {
                ((ApertureComponent) apIter.next()).visitUSB(morphology);
            } catch (Exception e) {
                System.out.println(" Could Not visit USB morphology with an aperture.");
            }

        }
    }

    public void visitExponential(Morphology3D morphology) {
        ListIterator apIter = apertureList.listIterator();
        while (apIter.hasNext()) {
            try {
                ((ApertureComponent) apIter.next()).visitExponential(morphology);
            } catch (Exception e) {
                System.out.println(" Could Not visit Exponential Morphology with an aperture.");
            }

        }
    }

    public void visitElliptical(Morphology3D morphology) {
        ListIterator apIter = apertureList.listIterator();
        while (apIter.hasNext()) {
            try {
                ((ApertureComponent) apIter.next()).visitElliptical(morphology);
            } catch (Exception e) {
                System.out.println(" Could Not visit Elliptical Morphology with an aperture.");
            }

        }
    }

    //Methods for distributing a getSourceFraction query to multiple apertures
    public List getFractionOfSourceInAperture() {
        List sourceFractionsList = new ArrayList();

        ListIterator apIter = apertureList.listIterator();
        while (apIter.hasNext()) {
            // there should only one source fraction per aperture.  Code might need to be
            // changed if we implement different clusters other than in a line.
            try {
                sourceFractionsList.add(
                        (((ApertureComponent) apIter.next())
                         .getFractionOfSourceInAperture()).get(0));
            } catch (Exception e) {
                System.out.println(" Could Not get Source Fraction.");
            }
        }

        return sourceFractionsList;
    }

}
