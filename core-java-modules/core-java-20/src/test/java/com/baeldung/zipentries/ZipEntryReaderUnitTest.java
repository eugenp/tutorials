package com.baeldung.zipentries;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertEquals;

public class ZipEntryReaderUnitTest {

    @Test
    public void givenZipFile_thenReadEntriesAndValidateContent() throws URISyntaxException, IOException {
        Path zipFilePath = Paths.get(getClass().getClassLoader().getResource("zipFile.zip").toURI());
        try (ZipFile zipFile = new ZipFile(zipFilePath.toString())) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {
                    try (InputStream inputStream = zipFile.getInputStream(entry);
                         Scanner scanner = new Scanner(inputStream);) {
                        String line = scanner.nextLine();
                        if ("fileA.txt".equals(entry.getName())) {
                            assertEquals(line, "this is the content in fileA");
                        }
                        if ("fileB.txt".equals(entry.getName())) {
                            assertEquals(line, "this is the content in fileB");
                        }
                    }
                }
            }
        }
    }
}
