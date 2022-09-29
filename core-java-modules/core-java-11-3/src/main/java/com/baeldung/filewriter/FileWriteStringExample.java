package com.baeldung.filewriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileWriteStringExample {
    public static String generateFileFromStringList(List<String> stringList) throws IOException {
        final String TEXT_FILENAME = "src/test/resources/sampleTextFile.txt";
        Path filePath = Paths.get(TEXT_FILENAME);
        Files.deleteIfExists(filePath);
        Files.createFile(filePath);
        for (String str : stringList) {
            Files.writeString(filePath, str + System.lineSeparator(),
                    StandardOpenOption.APPEND);
        }
        return filePath.toString();
    }
}
