package edu.gemini.itc.shared;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
	public static void write (String fileName, double [][] data) throws IOException{
		File file = new File("/tmp/"+fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
        //for (int i = 0; i < data.length; ++i) {
        	for (int j = 0; j < data[0].length; ++j)
        		bw.write(data[0][j] + " " + data[1][j] + "\n");
        	
        bw.close();
        fw.close();
		

	}
}
