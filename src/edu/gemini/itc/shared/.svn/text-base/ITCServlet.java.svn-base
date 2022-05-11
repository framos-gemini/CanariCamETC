// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ITCServlet.java,v 1.8 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.shared;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.gemini.itc.canaricam.CanaricamObsResult;

import java.util.Enumeration;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * Base class for ITC servlets.  Derive servlets for each instrument or instrument configuration.  Override the writeOutput() method to follow a recipe and perform a calculation. Some of the look of the output document is controled from here.
 */
public abstract class ITCServlet extends HttpServlet {

    public static int MAX_CONTENT_LENGTH = 1000000;  // Max file size 1MB

    /**
	 * Returns a title
	 * @uml.property  name="title"
	 */
    public abstract String getTitle();

    /**
	 * Returns version of the servlet.
	 * @uml.property  name="version"
	 */
    public abstract String getVersion();

    /**
	 * Returns the Instrument
	 * @uml.property  name="inst"
	 */
    public abstract String getInst();

    /** Subclasses supply the body content for the html document. */
    public abstract void writeOutput(HttpServletRequest req, PrintWriter out)
            throws Exception;

    /**
     * Called by server when form is submitted.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
			doPost(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServerInfo.setServerName(request.getServerName());
        ServerInfo.setServerPort(request.getServerPort());
        response.setContentType("text/html");
        long start = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        log("USER: " + request.getRemoteHost() + " DATE: " + now.getTime() + " SIZE: " + request.getContentLength());
        log("HTML: " + request.getHeader("Referer"));
        PrintWriter out = response.getWriter();
        openDocument(out);  // Write start of html document
        try {
            if (request.getContentLength() > MAX_CONTENT_LENGTH) {
                log("MAXFILE: " + request.getRemoteHost() + " BYTES: " + request.getContentLength() + " DATE: " + now.getTime());
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                try {
                    in.skip(request.getContentLength());
                } catch (java.io.IOException e) {
                }

                throw new Exception("File upload exceeds server limit of " + MAX_CONTENT_LENGTH / 1000000 + " MB. Resubmit with a smaller size. ");
            }
            writeOutput(request, out);  // perform calculation and write results
        } catch (Exception e) {
            closeDocument(out, e);  // close and show exception message
            return;
        }
        closeDocument(out); // Write close of html document
        out.close();
        long end = System.currentTimeMillis();
        log("CALCTIME: " + (end - start));
    }

    /** Write opening of html document */
    protected void openDocument(PrintWriter out) {
        out.println("<HTML><HEAD><TITLE>");
        out.println(getTitle() + " " + getVersion());
        out.println("</TITLE></HEAD>");
        out.println("<BODY text='#000000' bgcolor='#ffffff'>");
        out.println("<H2>" + getTitle() +
                    //" (DEVELOPMENT SERVER)" +
                    "<br>" + getInst() + " version " + getVersion() + "</H2>");
    }

    /** Write closing of html document */
    protected void closeDocument(PrintWriter out) {
        out.println("</BODY></HTML>");
    }

    /**
     * This is called when there is an exception in the middle of parsing
     * form data.  It prints diagnostic message and closes out the
     * html document.
     */
    protected void closeDocument(PrintWriter out, Exception e) {
        out.println("<pre>");
        out.println(e.getMessage() + "<br>");

        // temporary for debugging
        //out.println("<p><hr>Debugging<br>");
        //e.printStackTrace(out);

        // close out html docuemnt
        out.println("</pre>");
        out.println("</BODY></HTML>");
        out.close();
    }
    
}
