package com.baeldung.javafeatures;

import static java.io.IO.println;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class FindFolder {

    public static long numberOfFilesIn_classic(String path) {
        File currentFile = new File(path);
        File[] filesOrNull = currentFile.listFiles();
        long currentFileNumber = currentFile.isFile() ? 1 : 0;

        if (filesOrNull == null) {
            return currentFileNumber;
        }

        for (File file : filesOrNull) {
            if (file.isDirectory()) {
                currentFileNumber += numberOfFilesIn_classic(file.getAbsolutePath());
            } else if (file.isFile()) {
                currentFileNumber += 1;
            }
        }

        return currentFileNumber;
    }

    public static long numberOfFilesIn_Stream(String path) {
        File currentFile = new File(path);
        File[] filesOrNull = currentFile.listFiles();
        long currentFileNumber = currentFile.isFile() ? 1 : 0;

        if (filesOrNull == null) {
            return currentFileNumber;
        }

        return currentFileNumber + Arrays.stream(filesOrNull)
            .mapToLong(FindFolder::filesInside)
            .sum();
    }

    private static long filesInside(File it) {
        if (it.isFile()) {
            return 1;
        } else if (it.isDirectory()) {
            return numberOfFilesIn_Stream(it.getAbsolutePath());
        } else {
            return 0;
        }
    }

    public static long numberOfFilesIn_Walk(String path) {
        Path dir = Path.of(path);
        try (Stream<Path> stream = Files.walk(dir)) {
            return stream.parallel()
                .map(getFileOrEmpty())
                .flatMap(Optional::stream)
                .filter(it -> !it.isDirectory())
                .count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Function<Path, Optional<File>> getFileOrEmpty() {
        return it -> {
            try {
                return Optional.of(it.toFile());
            } catch (UnsupportedOperationException e) {
                println(e);
                return Optional.empty();
            }
        };
    }

    public static long numberOfFilesIn_NIO(String path) {
        try (Stream<Path> stream = Files.find(
            Paths.get(path),
            Integer.MAX_VALUE,
            (__, attr) -> attr.isRegularFile())
        ) {
            return stream.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

