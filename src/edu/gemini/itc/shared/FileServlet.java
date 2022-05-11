// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ImageServlet.java,v 1.6 2004/02/13 13:00:59 bwalls Exp $
//
package edu.gemini.itc.shared;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Image servlet for ITC. The instrument Servlet adds a tag the points to .
 * this servlet for each Image it wants to send the user. This servlet reads
 * the tag, opens the file and sends it to the user using a servlet output
 * stream.
 */
public final class FileServlet extends HttpServlet {
   
	
	private byte[] zipFiles(File directory, String[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];
 
        for (String fileName : files) {
        	if (fileName.contains(".txt")) {
	            FileInputStream fis = new FileInputStream(directory.getPath() + "/" + fileName);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            zos.putNextEntry(new ZipEntry(fileName));
	            int bytesRead;
	            while ((bytesRead = bis.read(bytes)) != -1) {
	                zos.write(bytes, 0, bytesRead);
	            }
	            zos.closeEntry();
	            bis.close();
	            fis.close();
        	}
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();
 
        return baos.toByteArray();
    }
	
    public void doGet(HttpServletRequest request, 
    				  HttpServletResponse response) throws ServletException, IOException {
        
    	String filename = request.getParameter("filename");
        String type = request.getParameter("type");
        if (filename.contains(".nm")) {
        	response.setContentType("text/plain");
        	response.setHeader("Content-Disposition","attachment;filename="+filename);
        }
        ServletOutputStream out = response.getOutputStream();
        if (filename.contains(".zip")) {
        	File directory = new File ("/tmp");
        	String [] files = directory.list();
        	byte[] zip = zipFiles(directory, files);
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=\"DATA.ZIP\"");
            out.write(zip);
        }
        else {
	        //create a Stream to pass the image through
	        InputStream input = FileServlet.class.getResourceAsStream(filename);
	        if (input == null) {
	        	// if file not exists in filesystem send 404 Error, or show default image.
	        	response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
	        	return;
	        }
	        BufferedInputStream bis = new BufferedInputStream(input);
	        int data;
	        while ((data = bis.read()) != -1) {
	        	out.write(data);
	        }
	        bis.close();
        }
        out.flush();
        out.close();
    }

    
    
}
