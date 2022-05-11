/*
 * IncompatableFileTypeException.java
 *
 * Created on November 30, 2001, 11:18 AM
 */

package edu.gemini.itc.shared;

/**
 *
 * @author  bwalls
 * @version
 */
public class IncompatibleFileTypeException extends java.lang.Exception {

    /**
     * Creates new <code>IncompatableFileTypeException</code> without detail message.
     */
    public IncompatibleFileTypeException() {
    }


    /**
     * Constructs an <code>IncompatableFileTypeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IncompatibleFileTypeException(String msg) {
        super(msg);
    }
}


