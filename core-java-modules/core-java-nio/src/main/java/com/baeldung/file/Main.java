package com.baeldung.file;

import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingApacheCommonsIO;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingBufferedReader;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingGoogleGuava;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingLineNumberReader;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingNIOFileChannel;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingNIOFiles;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingNIOFilesReadAllLines;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingScanner;

public class Main {

    private static final String INPUT_FILE_NAME = "src/main/resources/input.txt";

    public static void main(String... args) throws Exception {
        System.out.printf("Total Number of Lines Using BufferedReader: %s%n", getTotalNumberOfLinesUsingBufferedReader(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using LineNumberReader: %s%n", getTotalNumberOfLinesUsingLineNumberReader(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using Scanner: %s%n", getTotalNumberOfLinesUsingScanner(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using NIO Files: %s%n", getTotalNumberOfLinesUsingNIOFiles(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using NIO Files#readAllLines: %s%n", getTotalNumberOfLinesUsingNIOFilesReadAllLines(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using NIO FileChannel: %s%n", getTotalNumberOfLinesUsingNIOFileChannel(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using Apache Commons IO: %s%n", getTotalNumberOfLinesUsingApacheCommonsIO(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using NIO Google Guava: %s%n", getTotalNumberOfLinesUsingGoogleGuava(INPUT_FILE_NAME));
    }
}
