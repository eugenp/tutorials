package com.baeldung.fileToBase64StringConversion;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class FileToBase64StringConversionUnitTest {

	private String inputFilePath = "";
	private String outputFilePath = "";

	@Test
	// file paths are from environment arguments:
	// mvn test
	// -Dtest=com.baeldung.fileToBase64StringConversion.FileToBase64StringConversionUnitTest
	// -DinputFilePath="abc.png" -DoutFilePath="abc.png"

	public void fileToBase64StringConversion() throws FileNotFoundException, IOException {
		inputFilePath = System.getProperty("inputFilePath");
		outputFilePath = System.getProperty("outputFilePath");

		if (inputFilePath == null || outputFilePath == null)
			return;

		File outputFile = new File(outputFilePath);
		File inputFile = new File(inputFilePath);

		if (!inputFile.exists())
			return;

		byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
		String encodedString = Base64.getEncoder().encodeToString(fileContent);

		// print encoded base64 String
		System.out.println(encodedString);

		// decode the string and write to file
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		FileUtils.writeByteArrayToFile(outputFile, decodedBytes);

		assertTrue(outputFile.length() == fileContent.length);
	}
}
