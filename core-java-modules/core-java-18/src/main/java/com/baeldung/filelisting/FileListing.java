package com.baeldung.filelisting;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileListing {

    private static final Logger LOGGER = Logger.getLogger(FileListing.class.getName());

    /**
     * List files in a directory using Apache Commons IO
     * @param dir directory to list files
     */
    public static void listFilesCommonsIO(File dir) {
        Iterator<File> fileIterator = FileUtils.iterateFiles(dir, null, true);
        while (fileIterator.hasNext()) {
            File file = fileIterator.next();
            LOGGER.info("File: " + file.getAbsolutePath());
        }
    }

    /**
     * List files in a directory using Java NIO
     * @param dir directory to list files
     */
    public static void listFilesJavaNIO(Path dir) {
        try (Stream<Path> stream = Files.walk(dir)) {
            stream.filter(Files::isRegularFile).forEach(path -> LOGGER.info("File: " + path.toAbsolutePath()));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * List files in a directory using Java IO
     * @param dir directory to list files
     */
    public static void listFilesJavaIO(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                listFilesJavaIO(file);
            } else {
                LOGGER.info("File: " + file.getAbsolutePath());
            }
        }
    }
}