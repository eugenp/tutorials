package com.baeldung.files;

import static com.baeldung.files.NumberOfLineFinder.getTotalNumberOfLinesUsingApacheCommonsIO;
import static com.baeldung.files.NumberOfLineFinder.getTotalNumberOfLinesUsingBufferedReader;
import static com.baeldung.files.NumberOfLineFinder.getTotalNumberOfLinesUsingGoogleGuava;
import static com.baeldung.files.NumberOfLineFinder.getTotalNumberOfLinesUsingLineNumberReader;
import static com.baeldung.files.NumberOfLineFinder.getTotalNumberOfLinesUsingNIOFileChannel;
import static com.baeldung.files.NumberOfLineFinder.getTotalNumberOfLinesUsingNIOFiles;
import static com.baeldung.files.NumberOfLineFinder.getTotalNumberOfLinesUsingScanner;

public class Main {

    private static final String INPUT_FILE_NAME = "src/main/resources/input.txt";

    public static void main(String... args) throws Exception {
        System.out.printf("Total Number of Lines Using BufferedReader: %s%n", getTotalNumberOfLinesUsingBufferedReader(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using LineNumberReader: %s%n", getTotalNumberOfLinesUsingLineNumberReader(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using Scanner: %s%n", getTotalNumberOfLinesUsingScanner(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using NIO Files: %s%n", getTotalNumberOfLinesUsingNIOFiles(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using NIO FileChannel: %s%n", getTotalNumberOfLinesUsingNIOFileChannel(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using Apache Commons IO: %s%n", getTotalNumberOfLinesUsingApacheCommonsIO(INPUT_FILE_NAME));
        System.out.printf("Total Number of Lines Using NIO Google Guava: %s%n", getTotalNumberOfLinesUsingGoogleGuava(INPUT_FILE_NAME));
    }
}
