package com.baeldung.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListFiles {
    public static final int DEPTH = 1;

    public Set<String> listFilesUsingJavaIO(String dir) {
        Set<String> fileList = Stream.of(new File(dir).listFiles())
            .filter(file -> !Files.isDirectory(Paths.get(file.getAbsolutePath())))
            .map(file -> file.getName()
                .toString())
            .collect(Collectors.toSet());
        return fileList;
    }

    public Set<String> listFilesUsingFileWalk(String dir) throws IOException {
        Set<String> fileList;
        try (Stream<Path> stream = Files.walk(Paths.get(dir), DEPTH)) {
            fileList = stream.filter(file -> !Files.isDirectory(file))
                .map(file -> file.getFileName()
                    .toString())
                .collect(Collectors.toSet());
        }
        return fileList;
    }

    public Set<String> listFilesUsingFileWalkAndVisitor(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!Files.isDirectory(file))
                    fileList.add(file.getFileName()
                        .toString());
                return FileVisitResult.CONTINUE;
            }
        });
        return fileList;
    }

    public Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                        .toString());
                }
            }
        }
        return fileList;
    }

}
