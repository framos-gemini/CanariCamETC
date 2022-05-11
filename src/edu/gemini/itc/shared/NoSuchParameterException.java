/*
 * NoSuchParameterException.java
 *
 * Created on November 30, 2001, 11:21 AM
 */

package edu.gemini.itc.shared;

/**
 *
 * @author  bwalls
 * @version
 */
public class NoSuchParameterException extends java.lang.Exception {

    /**
	 * @uml.property  name="parameterName"
	 */
    public java.lang.String parameterName;

    /**
     * Creates new <code>NoSuchParameterException</code> without detail message.
     */
    public NoSuchParameterException() {
    }


    /**
     * Constructs an <code>NoSuchParameterException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NoSuchParameterException(String msg, String parameterName) {
        super(msg);
        this.parameterName = parameterName;
    }
}


