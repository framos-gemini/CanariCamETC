// Copyright 2001 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
package edu.gemini.itc.shared;

import java.util.List;


/**
 * This is the abstract class that plays the role of Component in the composite pattern from GoF book.  This allows an aperture or a group of apertures to be treated the same by the program.  This will be helpful when we implement multiple IFU's. The class also plays the role of Visitor to a morphology.  This allows the class to calculate different values of the SourceFraction for different types of Morphologies.
 */
public abstract class ApertureComponent implements MorphologyVisitor {
    /**
	 * @uml.property  name="fractionOfSourceInAperture"
	 */
    public abstract List getFractionOfSourceInAperture();
}
