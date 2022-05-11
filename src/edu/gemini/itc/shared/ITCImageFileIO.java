// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: ITCImageFileIO.java,v 1.9 2004/03/17 13:42:16 bwalls Exp $
//
package edu.gemini.itc.shared;


import javax.servlet.ServletException;

//import com.klg.jclass.util.swing.encode.PNGEncoder;
//import com.klg.jclass.util.swing.encode.EncoderException;
//import com.klg.jclass.chart.*;

import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

import java.awt.Image;
import java.awt.image.BufferedImage;

import org.jfree.chart.ChartUtilities;

//import javax.media.jai.*;
//import java.awt.image.renderable.ParameterBlock;
import javax.servlet.ServletOutputStream;

public class ITCImageFileIO {

    //Try to use the /tmp dir.  Every UNIX machine has one.
    /**
	 * @uml.property  name="_directory"
	 */
    private String _directory = "/tmp";
    /**
	 * @uml.property  name="_subDir"
	 */
    private String _subDir = "/ITCTempImages";
    /**
	 * @uml.property  name="_tempDir"
	 */
    private File _tempDir = new File(File.separator + _directory +
                                     File.separator + _subDir);

    /**
	 * @uml.property  name="_imagePath"
	 */
    private String _imagePath;
    /**
	 * @uml.property  name="_fileName"
	 */
    private String _fileName = "";


    public ITCImageFileIO() //throws Exception
    {
        //Include code here to check Directory structure and Exit
        // if it cannot complete its task.

        if (!(_tempDir.exists())) {
            _tempDir.mkdir();
        }
        setImagePath(_tempDir.getPath());
    }

    public void sendFiletoServOut(String filename, ServletOutputStream out)
            throws ServletException, IOException {
        try {
            File fileToDelete = new File(getImagePath() + File.separator +
                                         filename);
            BufferedInputStream bis = new BufferedInputStream(
                    						new FileInputStream(getImagePath() + 
                    						File.separator +
                    						filename));
            int data;
            while ((data = bis.read()) != -1) {
                out.write(data);
            }
            bis.close();

            out.flush();
            //fileToDelete.delete();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void sendFiletoServOut(BufferedImage image, ServletOutputStream out)
            throws ServletException, IOException {
        try {
            ImageIO.write(image, "png", out);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void saveCharttoDisk(Image tmpChart) throws IOException{//, EncoderException { //add for KLG
        try {
            //long startTime =System.currentTimeMillis();

            //PNGEncoder enc = new PNGEncoder();  // ADD for KLG
            File tmpOut = new File(getImagePath());
            File randomFileName = tmpOut.createTempFile("SessionID", ".png", tmpOut);
           
            FileOutputStream Outfile = new FileOutputStream(randomFileName);
            OutputStream out = new BufferedOutputStream(Outfile);

            FileImageOutputStream fios = new FileImageOutputStream(randomFileName);
            ImageIO.write((BufferedImage)tmpChart,"PNG",fios);  // 100 ms requires java 1.4  smaller file size
                                                                 // could use the full imageIO capability to turn
                                                                 // down compression for faster.
            //ChartUtilities.writeBufferedImageAsPNG(out,(BufferedImage)tmpChart);  //20 Seconds
            //enc.encode(tmpChart, out);  // ave 300 ms requires KLG :(
            out.flush();
            out.close();
            //long stopTime = System.currentTimeMillis();
            //System.out.println("Encode Elapsed Time: " + (stopTime - startTime));

            setFileName(randomFileName.getName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public void saveSedtoDisk(String header, SampledSpectrum sed) throws IOException {
        try {
            File tmpOut = new File(getImagePath());
            File randomFileName = tmpOut.createTempFile("SessionID", ".dat", tmpOut);
            FileOutputStream Outfile = new FileOutputStream(randomFileName);
            OutputStream out = new BufferedOutputStream(Outfile);
            DataOutputStream dout = new DataOutputStream(out);
            String specOutput = sed.printSpecAsString();
            dout.writeBytes(header + " \n" + specOutput);
            dout.flush();
            dout.close();
            setFileName(randomFileName.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveSedtoDisk(String header, SampledSpectrum sed, String randomFilename) throws IOException {
        try {
            File tmpOut = new File(getImagePath() + "/" + randomFilename);
            //File randomFileName = tmpOut.createTempFile("SessionID",".dat", tmpOut);
            FileOutputStream Outfile = new FileOutputStream(getImagePath() + "/" + randomFilename,true);  //append if exists
            OutputStream out = new BufferedOutputStream(Outfile);
            DataOutputStream dout = new DataOutputStream(out);
            String specOutput = sed.printSpecAsString();
            
            if (tmpOut.length()>10) {
                dout.writeBytes(specOutput);  //if the file already existed then just append spectrum to it.
            } else {
                dout.writeBytes(header + " \n" + specOutput);
            }
            dout.flush();
            dout.close();
            //setFileName(randomFileName.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public String getRandomFileName(String extention) throws IOException {
        File randomFileName = null;

        try {
            File tmpOut = new File(getImagePath());
            randomFileName = tmpOut.createTempFile("SessionID", extention, tmpOut);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return randomFileName.getName();
    }


    private String getImagePath() {
        return _imagePath;
    }

    private void setImagePath(String imagePath) {
        _imagePath = imagePath;
    }

    public String getFileName() {
        return _fileName;
    }

    private void setFileName(String fileName) {
        _fileName = fileName;
    }

}
