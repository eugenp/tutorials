package com.baeldung.zipentries;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipEntryReader {

    public static void readZipEntries(String filePath) throws IOException {
        try (ZipFile zipFile = new ZipFile(filePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                // Check if entry is a directory
                if (!entry.isDirectory()) {
                    // Read and process the entry contents using the inputStream
                    try (InputStream inputStream = zipFile.getInputStream(entry);
                         Scanner scanner = new Scanner(inputStream);) {
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            System.out.println(line);
                        }
                    }
                }
            }
        }
    }
}
