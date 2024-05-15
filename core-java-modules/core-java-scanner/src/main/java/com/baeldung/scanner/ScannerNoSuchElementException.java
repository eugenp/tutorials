package com.baeldung.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerNoSuchElementException {

    public static String readFileV1(String pathname) throws IOException {
        Path pathFile = Paths.get(pathname);
        if (Files.notExists(pathFile)) {
            return "";
        }

        try (Scanner scanner = new Scanner(pathFile)) {
            return scanner.nextLine();
        }
    }

    public static String readFileV2(String pathname) throws IOException {
        Path pathFile = Paths.get(pathname);
        if (Files.notExists(pathFile)) {
            return "";
        }

        try (Scanner scanner = new Scanner(pathFile)) {
            return scanner.hasNextLine() ? scanner.nextLine() : "";
        }
    }

    public static String readFileV3(String pathname) throws IOException {
        Path pathFile = Paths.get(pathname);
        if (Files.notExists(pathFile) || Files.size(pathFile) == 0) {
            return "";
        }

        try (Scanner scanner = new Scanner(pathFile)) {
            return scanner.nextLine();
        }
    }

    public static String readFileV4(String pathname) throws IOException {
        Path pathFile = Paths.get(pathname);
        if (Files.notExists(pathFile)) {
            return "";
        }

        try (Scanner scanner = new Scanner(pathFile)) {
            return scanner.nextLine();
        } catch (NoSuchElementException exception) {
            return "";
        }
    }

}
