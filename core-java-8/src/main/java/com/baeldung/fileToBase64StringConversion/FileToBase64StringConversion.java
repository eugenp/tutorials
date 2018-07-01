package com.baeldung.fileToBase64StringConversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FileToBase64StringConversion {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//read file path from first argument
		String filePath = args[0];
		
		byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);

		//print encoded base64 String
		System.out.println(encodedString);
		
		//construct output file name
		String extension = FilenameUtils.getExtension(filePath);
		String baseFileName = FilenameUtils.getBaseName(filePath);
		String directory = new File(filePath).getParentFile().getAbsolutePath();
		String outputFileName = directory+File.separator+baseFileName+"_copy."+extension;
		
		//decode the string and write to file
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		FileUtils.writeByteArrayToFile(new File(outputFileName), decodedBytes);
	}
}
