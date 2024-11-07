package com.baeldung.s3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;

class FileGeneratorUtil {

    private static final String FILE_EXTENSION = ".txt";

    public static File generate() throws IOException {
        String fileName = "test_" + UUID.randomUUID().toString();
        Path filePath = Files.createTempFile(fileName, FILE_EXTENSION, new FileAttribute<?>[0]);
        return filePath.toFile();
    }

}