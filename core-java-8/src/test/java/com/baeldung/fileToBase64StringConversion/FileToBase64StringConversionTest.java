package com.baeldung.fileToBase64StringConversion;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class FileToBase64StringConversionTest {

	private String inputFilePath;
	private String outputFilePath;

	public FileToBase64StringConversionTest(String inputFilePath, String outputFilePath) {
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
	}

	@Parameterized.Parameters
	public static Collection<String[]> parameters() {
		return Arrays.asList(new String[][] { { "", "" } });
	}

	@Test
	public void fileToBase64StringConversion() throws FileNotFoundException, IOException {
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
