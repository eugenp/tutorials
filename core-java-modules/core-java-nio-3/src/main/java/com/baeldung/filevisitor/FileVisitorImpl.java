package com.baeldung.filevisitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitorImpl implements FileVisitor<Path> {

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return null;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        return null;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return null;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return null;
    }
}