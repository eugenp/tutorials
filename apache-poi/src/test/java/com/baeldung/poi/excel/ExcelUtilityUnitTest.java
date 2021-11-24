package com.baeldung.poi.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class ExcelUtilityUnitTest {
	private static final String FILE_NAME = "baeldung.xlsx";
	private String fileLocation;
	private static final String ENDLINE = System.getProperty("line.separator");
	private StringBuilder output;

	@Before
	public void setupUnitTest() throws IOException, URISyntaxException {
		output = new StringBuilder();
		output.append("--------------------------------------------------------------------").append(ENDLINE);
		output.append("Worksheet :Sheet1").append(ENDLINE);
		output.append("--------------------------------------------------------------------").append(ENDLINE);
		output.append("|| Name1 | Surname1 | 3.55696564113E11 | Thu Nov 04 00:00:00 CET 2021 | ‡ |  ||")
				.append(ENDLINE);
		output.append("|| Name2 | Surname2 | 5.646513512E9 | Sat Dec 04 00:00:00 CET 2021 | false |  ||")
				.append(ENDLINE);
		output.append("|| Name3 | Surname3 | 3.55696564113E11 | Thu Nov 04 00:00:00 CET 2021 | 7.17039641738E11 |  ||")
				.append(ENDLINE);
		output.append("--------------------------------------------------------------------").append(ENDLINE);
		output.append("Worksheet :Sheet2").append(ENDLINE);
		output.append("--------------------------------------------------------------------").append(ENDLINE);
		output.append("|| Name4 | Surname4 | 3.55675623232E11 | 13/04/2021 |  ||").append(ENDLINE);

		fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
	}

	@Test
	public void givenStringPath_whenReadExcel_thenReturnStringValue() throws IOException {
		assertEquals(output.toString(), ExcelUtility.readExcel(fileLocation));

	}

	@Test
	public void givenStringPath_whenReadExcel_thenThrowException() {
		assertThrows(IOException.class, () -> {
			ExcelUtility.readExcel("baeldung");
		});
	}

}
