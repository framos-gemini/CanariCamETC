// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
//
//

package edu.gemini.itc.canaricam;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.gemini.itc.shared.ITCMultiPartParser;
import edu.gemini.itc.shared.ITCServlet;
import edu.gemini.itc.shared.Recipe;
import edu.gemini.itc.shared.ServerInfo;

/**
 * This servlet accepts form data from the ITC html document.
 * <br></br>
 * Multithreading issues:
 * Normally a servlet could be called on multiple threads to process
 * simultaneous requests.  We could either take care to synchronize
 * properly or use the SingleThreadModel interface.
 * SingleThreadModel is a marker interface (i.e. has no methods) that tells
 * the server to use a separate instance of this servlet for each
 * request.  Use this for now.
 * Note that since this servlet is single threaded, any other
 * servlet wanting to call its methods should make an HTTP request
 * rather than calling the methods directly.
 * /**
 * This servlet accepts form data from the ITC html document.
 * <br></br>
 * Multithreading issues:
 * Normally a servlet could be called on multiple threads to process
 * simultaneous requests.  We could either take care to synchronize
 * properly or use the SingleThreadModel interface.
 * SingleThreadModel is a marker interface (i.e. has no methods) that tells
 * the server to use a separate instance of this servlet for each
 * request.  Use this for now.
 * Note that since this servlet is single threaded, any other
 * servlet wanting to call its methods should make an HTTP request
 * rather than calling the methods directly.
 
public final class ITCCanariCamServlet extends HttpServlet //extends ITCServlet implements SingleThreadModel
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");
        PrintWriter printWriter  = response.getWriter();
        printWriter.println("<h1>Hello World!</h1>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}

*/

public final class ITCCanariCamServlet extends ITCServlet implements SingleThreadModel
{
   public static final String VERSION="5.0";
   public static final String TITLE="CanariCam Exposure Time Calculator";
   public static final String INSTRUMENT = "CanariCam";

   /** Returns a title */
   public String getTitle() { return TITLE; }
   /** Returns a version of this servlet */
   public String getVersion() { return VERSION; }
   /** Returns the Instrument name*/
   public String getInst() {return INSTRUMENT; }

   /**
    * Describes the purpose of the servlet.
    * Used by Java Web Server Administration Tool.
    */
   public String getServletInfo() {
      return getTitle() + " " + getVersion() +
	  " - ITCCanariCamServlet accepts form data and performs ITC calculation for CanariCam.";
   }
   
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       //super.doPost(request, response);
	   //response.setContentType("text/plain;charset=UTF-8");
	   ServerInfo.setServerName(request.getServerName());
       ServerInfo.setServerPort(request.getServerPort());
	   response.setContentType("text/html");
	   StringWriter sw = new StringWriter();
	   PrintWriter out = new PrintWriter(sw);
       // Perform calculation, write the output to the web page.
       try {
    	   CanariCamRecipe canRecipe = null;
    	   openDocument(out);
           if (request.getContentType().startsWith("multipart/form-data")) {
        	   canRecipe = new CanariCamRecipe( new ITCMultiPartParser(request, MAX_CONTENT_LENGTH), out );
           }
           else 
        	   canRecipe = new CanariCamRecipe(request, out); // parses form data

         canRecipe.writeOutput();
		 RequestDispatcher dispatch = request.getRequestDispatcher(canRecipe.getObsResult().getView());
		 request.setAttribute("instrument", canRecipe.getInstrument());
		 request.setAttribute("obsResult", canRecipe.getObsResult());
		 request.setAttribute("sdParameters", canRecipe.getSdParameters());
		 request.setAttribute("ccParameters", canRecipe.getCCParameters());
		 request.setAttribute("obsConditionParameters", canRecipe.getObsConditionParameters());
		 request.setAttribute("obsDetailParameters", canRecipe.getObsDetailParameters());
		 request.setAttribute("textPlane", sw.toString());
		 dispatch.forward(request, response);  
       } catch (Exception e) {
    	   e.printStackTrace();
    	   closeDocument(out, e);
    	   response.getWriter().write(sw.toString());
       }
   }
   
   
   /** Supply the body content for the html document. */
   public void writeOutput(HttpServletRequest r, PrintWriter out) throws Exception {
       Recipe recipe;

       if (r.getContentType().startsWith("multipart/form-data")) {
    	   recipe = new CanariCamRecipe( new ITCMultiPartParser(r, MAX_CONTENT_LENGTH), out );
       }
       else recipe = new CanariCamRecipe(r, out); // parses form data

       // Perform calculation, write the output to the web page.
       recipe.writeOutput();
   }
}
