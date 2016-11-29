package com.baeldung.java.nio2.visitor;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSearchExample implements FileVisitor<Path> {
    private static String FILE_NAME;
    private static Path START_DIR;

    public FileSearchExample(String fileName, Path startingDir) {
        FILE_NAME = fileName;
        START_DIR = startingDir;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String fileName = file.getFileName().toString();
        if (FILE_NAME.equals(fileName)) {
            System.out.println("File found: " + file.toString());
            return TERMINATE;
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Failed to access file: " + file.toString());
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        boolean finishedSearch = Files.isSameFile(dir, START_DIR);
        if (finishedSearch) {
            System.out.println("File:" + FILE_NAME + " not found");
            return TERMINATE;
        }
        return CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        Path startingDir = Paths.get("C:/Users/new/Desktop");
        FileSearchExample crawler = new FileSearchExample("hibernate.txt", startingDir);
        Files.walkFileTree(startingDir, crawler);
    }

}
