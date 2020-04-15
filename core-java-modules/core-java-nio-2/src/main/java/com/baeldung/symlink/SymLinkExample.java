package com.baeldung.symlink;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class SymLinkExample {

    public void createSymbolicLink(Path link, Path target) throws IOException {
        if (Files.exists(link)) {
            Files.delete(link);
        }
        Files.createSymbolicLink(link, target);
    }

    public void createHardLink(Path link, Path target) throws IOException {
        if (Files.exists(link)) {
            Files.delete(link);
        }
        Files.createLink(link, target);
    }

    public Path createTextFile() throws IOException {
        byte[] content = IntStream.range(0, 10000)
            .mapToObj(i -> i + System.lineSeparator())
            .reduce("", String::concat)
            .getBytes(StandardCharsets.UTF_8);
        Path filePath = Paths.get(".", "target_link.txt");
        Files.write(filePath, content, CREATE, TRUNCATE_EXISTING);
        return filePath;
    }

    public void printLinkFiles(Path path) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    printLinkFiles(file);
                } else if (Files.isSymbolicLink(file)) {
                    System.out.format("File link '%s' with target '%s'%n", file, Files.readSymbolicLink(file));
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new SymLinkExample().printLinkFiles(Paths.get("."));
    }

}
