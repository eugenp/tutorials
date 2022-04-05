package com.baeldung.jimfs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileManipulation {

    void move(final Path origin, final Path destination) {
        try {
            Files.createDirectories(destination);
            Files.move(origin, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
