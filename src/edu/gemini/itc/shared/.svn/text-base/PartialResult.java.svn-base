// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ImageServlet.java,v 1.6 2004/02/13 13:00:59 bwalls Exp $
//
package edu.gemini.itc.shared;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Image servlet for ITC. The instrument Servlet adds a tag the points to .
 * this servlet for each Image it wants to send the user. This servlet reads
 * the tag, opens the file and sends it to the user using a servlet output
 * stream.
 */
public final class PartialResult extends HttpServlet {
  
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, 
    				  HttpServletResponse response) throws ServletException, IOException {
        
		ITCChart CanariCamChart = new ITCChart();
		ServerInfo.setServerName(request.getServerName());
		ServerInfo.setServerPort(8080);
		PrintWriter out2 = response.getWriter();
		out2.println("<HTML><HEAD><TITLE>");
        out2.println("PARTIALS RESULTS");
        out2.println("</TITLE></HEAD>");
        out2.println("<BODY>");
    	//String filename = request.getParameter("filename");
    	StringBuffer _header = new StringBuffer("# CanariCam ITC: \n");
        String [] files = new File("/tmp/").list();
        List<String> listFiles = new ArrayList<String>();
        for (String f : files) 
        	if (f.contains(".txt"))
        		listFiles.add(f);
        
        java.util.Collections.sort(listFiles, Collections.reverseOrder());
        BufferedReader br = null;
        
        for (String fileName : listFiles) {
        	System.out.println(fileName);
        		FileReader fr = new FileReader("/tmp/"+fileName);
        		br = new BufferedReader(fr);
        		String line;
        		double[][] dataAux = new double[2][50000];
        		int i = 0;
        		while ((line = br.readLine()) != null){
        			//System.out.println(line);
        			String aux[] = line.split(" ");
        			dataAux[0][i] = Double.parseDouble(aux[0]);
        			dataAux[1][i] = Double.parseDouble(aux[1]);
        			++i;
        		}
        		double[][] data = new double[2][i];
        		for (int k = 0; k < 2; ++k)
        			for (int z = 0; z < i; ++z)
        				data[k][z] = dataAux[k][z];
        		
        		System.out.println(fileName);
        		CanariCamChart.addArray(data, fileName);
        		CanariCamChart.addTitle(fileName);
        		CanariCamChart.autoscale();
        		
        		try {
					_println(CanariCamChart.getBufferedImage(),fileName, out2, _header, fileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
        		CanariCamChart.flush();
          		br.close();
          		fr.close();
        	//}
        	
        }
        out2.println("</BODY></HTML>");
        out2.flush();
        out2.close();
    }
	
	private void _print(String s,  PrintWriter _out,StringBuffer _header) {
        if (_out == null) {
            System.out.print(replace(s,"<br>","\n"));
        } else {
            _out.print(replace(s,"\n","<br>"));
        }
        s= replace(s,"<br>","\n");
        s= replace(s,"<BR>","\n");
        s= replace(s,"<LI>","-");
        
        if(!s.equals(""))
            if (s.charAt(0)!='<')
                _header.append("# "+s  + "\n");
    }
	
	 private void _println(BufferedImage image, String imageName, PrintWriter out,StringBuffer _header, String fileName) {
	        ITCImageFileIO FileIO = new ITCImageFileIO();
	        
	        try{
	            FileIO.saveCharttoDisk(image);
	        } catch (Exception ex) { 
	        	System.out.println("Unable to save file");
	        	ex.printStackTrace();
	        }
	        out.print("<h3>"+fileName+"</h3>");
	        _print("<IMG alt=\""+FileIO.getFileName()+
	        		"\" height=500 src=\""+
	        		ServerInfo.getServerURL() +
	        		"itc/servlet/images?type=img&filename="+
	        		FileIO.getFileName()+"\" width=675 border=0>", out, _header);
	        
	    }
	 
	 static String replace(String str, String pattern, String replace) {
	        int s = 0;
	        int e = 0;
	        StringBuffer result = new StringBuffer();
	        
	        while ((e = str.indexOf(pattern, s)) >= 0) {
	            result.append(str.substring(s, e));
	            result.append(replace);
	            s = e+pattern.length();
	        }
	        result.append(str.substring(s));
	        return result.toString();
	    }
}
