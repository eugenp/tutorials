package com.baeldung.findfiles;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FindFileJava7Utils {

    private FindFileJava7Utils() {
    }

    public static List<Path> find(Path startPath, String extension) throws IOException {
        if (!Files.isDirectory(startPath)) {
            throw new IllegalArgumentException("Provided path is not a directory: " + startPath);
        }

        final List<Path> matches = new ArrayList<>();
        MatchExtensionPredicate filter = new MatchExtensionPredicate(extension);

        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                if (filter.test(file)) {
                    matches.add(file);
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });

        return matches;
    }
}
