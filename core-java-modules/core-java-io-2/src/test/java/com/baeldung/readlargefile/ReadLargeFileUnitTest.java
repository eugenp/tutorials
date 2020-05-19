package com.baeldung.readlargefile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

@Ignore("need large file for testing")
public class ReadLargeFileUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // tests - iterate lines in a file

    @Test
    public final void givenUsingGuava_whenIteratingAFile_thenCorrect() throws IOException {
        final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";

        logMemory();
        Files.readLines(new File(path), Charsets.UTF_8);
        logMemory();
    }

    @Test
    public final void givenUsingCommonsIo_whenIteratingAFileInMemory_thenCorrect() throws IOException {
        // final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";

        logMemory();
        FileUtils.readLines(new File(path));
        logMemory();
    }

    @Test
    public final void whenStreamingThroughAFile_thenCorrect() throws IOException {
        final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";

        logMemory();

        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                final String line = sc.nextLine();
                // System.out.println(line);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }

        logMemory();
    }

    @Test
    public final void givenUsingApacheIo_whenStreamingThroughAFile_thenCorrect() throws IOException {
        final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";

        logMemory();

        final LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8");
        try {
            while (it.hasNext()) {
                final String line = it.nextLine();
                // do something with line
            }
        } finally {
            LineIterator.closeQuietly(it);
        }

        logMemory();
    }

    // utils

    private final void logMemory() {
        logger.info("Max Memory: {} Mb", Runtime.getRuntime()
            .maxMemory() / 1048576);
        logger.info("Total Memory: {} Mb", Runtime.getRuntime()
            .totalMemory() / 1048576);
        logger.info("Free Memory: {} Mb", Runtime.getRuntime()
            .freeMemory() / 1048576);
    }

}
