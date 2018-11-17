package com.baeldung.hexagon;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HolidayFileService implements HolidayService {

	@Override
	public boolean dateExists(String dateInMMDDYYYYFormat) {
		boolean dateFound = false;
		try {
			List<String> allLines = Files.readAllLines(Paths.get("E:\\holidays.txt"), StandardCharsets.UTF_8);
			dateFound = allLines.contains(dateInMMDDYYYYFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dateFound;
	}
}
