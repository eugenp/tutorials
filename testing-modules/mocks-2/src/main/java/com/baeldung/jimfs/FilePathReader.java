package com.baeldung.jimfs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

class FilePathReader {

    String getSystemPath(final Path path) {
        try {
            return path
              .toRealPath()
              .toString();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
