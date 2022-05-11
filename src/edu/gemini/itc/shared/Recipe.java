// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: Recipe.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

import java.io.PrintWriter;

/**
 * Interface for ITC recipes.
 * By convention a recipe constructor should take as arguments a
 * HttpServletRequest containing the form data and a PrintWriter
 * to which the calculation results are written.
 */
public interface Recipe {
    /**
     * Writes results of the recipe calculation.
     * Format should be suitable for inclusion inside a web page.
     */
    void writeOutput() throws Exception;
    
    ObservationResult getObsResult() throws Exception;
}
