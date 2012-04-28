package ru.crystalservice.setquery;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileHelper {
	public static String getContents(File aFile, String encoding) throws Exception {
		StringBuilder contents = new StringBuilder();
		InputStreamReader isr;
		if ((encoding != null) && !encoding.equals(""))
			isr = new InputStreamReader(new FileInputStream(aFile), encoding);
		else
			isr = new InputStreamReader(new FileInputStream(aFile));
		BufferedReader input = new BufferedReader(isr);
		try {
			String line = null; // not declared within while loop
			while ((line = input.readLine()) != null) {
				contents.append(line);
				contents.append(System.getProperty("line.separator"));
			}
		} finally {
			input.close();
			isr.close();
		}
		return contents.toString();
	}

	public static void setContents(File aFile, String aContents, String encoding) throws IOException {
		OutputStreamWriter osw;
		if ((encoding != null) && !encoding.equals(""))
			osw = new OutputStreamWriter(new FileOutputStream(aFile), encoding);
		else
			osw = new OutputStreamWriter(new FileOutputStream(aFile));
		Writer output = new BufferedWriter(osw);
		try {
			output.write(aContents);
		} finally {
			output.flush();
			osw.flush();
			output.close();
			osw.close();
		}
	}



	
}
