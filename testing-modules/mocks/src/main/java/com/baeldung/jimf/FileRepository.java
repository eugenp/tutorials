package com.baeldung.jimf;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileRepository {

    void create(final Path path, final String fileName) {
        final Path filePath = path.resolve(fileName);
        try {
            Files.createFile(filePath);
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    String read(final Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    String update(final Path path, final String newContent) {
        try {
            Files.write(path, newContent.getBytes());
            return newContent;
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    void delete (final Path path){
        try {
            Files.deleteIfExists(path);
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
