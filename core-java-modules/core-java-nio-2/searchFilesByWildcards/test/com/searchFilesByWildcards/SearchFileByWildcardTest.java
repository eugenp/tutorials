package com.searchFilesByWildcards;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class SearchFileByWildcardTest {
	@Test
	void whenThreeMatch_thenCountThree() throws IOException {
		SearchFileByWildcard sfbw = new SearchFileByWildcard();
		int c1 = sfbw.searchWithWc(Paths.get("C:\\temp\\test"), "glob:*.{txt,docx}");
		
		assertEquals(3, c1);
	}
	@Test
	void whenOneMatch_thenCountOne() throws IOException {
		SearchFileByWildcard sfbw = new SearchFileByWildcard();
		int c1 = sfbw.searchWithWc(Paths.get("C:\\temp\\test"), "glob:????.{csv}");
		
		assertEquals(1, c1);
	}
}
