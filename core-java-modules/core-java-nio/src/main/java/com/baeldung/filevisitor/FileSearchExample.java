package com.baeldung.filevisitor;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

public class FileSearchExample implements FileVisitor<Path> {
    private final String fileName;
    private final Path startDir;

    public FileSearchExample(String fileName, Path startingDir) {
        this.fileName = fileName;
        startDir = startingDir;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String fileName = file.getFileName().toString();
        if (this.fileName.equals(fileName)) {
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
        boolean finishedSearch = Files.isSameFile(dir, startDir);
        if (finishedSearch) {
            System.out.println("File:" + fileName + " not found");
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