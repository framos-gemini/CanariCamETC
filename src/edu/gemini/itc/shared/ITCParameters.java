// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ITCParameters.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

import javax.servlet.http.HttpServletRequest;

/**
 * This class supplies static methods used by the parameter parsing classes.
 */
public abstract class ITCParameters {

    /** Parse parameters from a servlet request. */
    public abstract void parseServletRequest(HttpServletRequest r)
            throws Exception;

    public static void notFoundException(String s) throws Exception {
        throw new Exception("Can't find " + s);
    }

    /**
     * Try to parse given string, throw exception on failure
     * Exception will mention the description
     */
    public static int parseInt(String string,
                               String description) throws Exception {
        if (string == null) {
            throw new Exception(description + " - null input");
        }
        string = string.trim();
        if (string.equals("")) {
            throw new Exception(description + " - empty input");
        }
        int i;
        try {
            i = parseInt(string);
        } catch (Exception e) {
            throw new Exception(description +
                                " - can't parse into integer: " + string);
        }
        return i;
    }

    /** Try to parse given string, throw informative exception on failure */
    public static int parseInt(String string) throws Exception {
        if (string == null) {
            throw new Exception("null input");
        }
        string = string.trim();
        if (string.equals("")) {
            throw new Exception("empty input");
        }
        int i;
        try {
            i = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new Exception("Can't parse into integer: " + string);
        }
        return i;
    }

    /**
     * Try to parse given string, throw exception on failure
     * Exception will mention the description
     */
    public static double parseDouble(String string,
                                     String description) throws Exception {
        if (string == null) {
            throw new Exception(description + " - null input");
        }
        string = string.trim();
        if (string.equals("")) {
            throw new Exception(description + " - empty input");
        }
        double d;
        try {
            d = parseDouble(string);
        } catch (Exception e) {
            throw new Exception(description +
                                " - can't parse into floating point number: " + string);
        }
        return d;
    }

    /** Try to parse given string, throw informative exception on failure */
    public static double parseDouble(String string) throws Exception {
        if (string == null) {
            throw new Exception("null input");
        }
        string = string.trim();
        if (string.equals("")) {
            throw new Exception("empty input");
        }
        double d;
        try {
            d = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            throw new Exception("Can't parse into floating point number: " +
                                string);
        }
        return d;
    }
}
