/*
 * ParameterParseException.java
 *
 * Created on November 30, 2001, 11:40 AM
 */

package edu.gemini.itc.shared;

/**
 *
 * @author  bwalls
 * @version
 */
public class ParameterParseException extends java.lang.Exception {

    /**
	 * @uml.property  name="parameterName"
	 */
    public String parameterName;

    /**
     * Creates new <code>ParameterParseException</code> without detail message.
     */
    public ParameterParseException() {
    }


    /**
     * Constructs an <code>ParameterParseException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ParameterParseException(String msg, String parameterName) {
        super(msg);
        this.parameterName = parameterName;
    }
}


