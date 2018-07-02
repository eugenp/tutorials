package com.baeldung.symlink;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class SymLinkExampleUnitTest {

	@Test
	public void testCreateSymbolicLink() throws IOException {
		SymLinkExample example = new SymLinkExample();
		Path filePath = example.createTextFile();
		Path link = example.createSymbolicLink("./symbolic_link.txt", filePath).get();
		assertTrue(Files.isSymbolicLink(link));	
	}

	@Test
	public void testCreateHardLink() throws IOException {
		SymLinkExample example = new SymLinkExample();
		Path filePath = example.createTextFile();
		Path link = example.createHardLink("./hard_link.txt", filePath).get();
		assertFalse(Files.isSymbolicLink(link));
		assertEquals(filePath.toFile().length(), link.toFile().length());
	}

}
