// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ImageServlet.java,v 1.6 2004/02/13 13:00:59 bwalls Exp $
//
package edu.gemini.itc.shared;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.Image;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

/**
 * Image servlet for ITC. The instrument Servlet adds a tag the points to .
 * this servlet for each Image it wants to send the user. This servlet reads
 * the tag, opens the file and sends it to the user using a servlet output
 * stream.
 */
public final class ImageServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String IMG = "img";
    private static final String TXT = "txt";

    /**
	 * @uml.property  name="_filename"
	 */
    private String _filename;
    /**
	 * @uml.property  name="_type"
	 */
    private String _type;
    /**
	 * @uml.property  name="_sessionObject"
	 * @uml.associationEnd  
	 */
    private HttpSession _sessionObject = null;

    /**
	 * @uml.property  name="servFileIO"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    public ITCImageFileIO ServFileIO = new ITCImageFileIO();

    /**
     * Called by server when an image is requested.
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        String filename = request.getParameter("filename");
        _type = request.getParameter("type");
        _sessionObject = request.getSession(true);
        if (_type.equals(IMG)) {
            // set the content type to image png
            response.setContentType("image/png");
            //response.setHeader("Cache-Control", "no-cache");
        } else if (_type.equals(TXT)) {
            response.setContentType("text/plain");
        } else {
            response.setContentType("text/plain");
        }
        //create a Stream to pass the image through
        ServletOutputStream out = response.getOutputStream();
        ServFileIO.sendFiletoServOut(filename, out);

        out.flush();
        out.close();
    }

}
