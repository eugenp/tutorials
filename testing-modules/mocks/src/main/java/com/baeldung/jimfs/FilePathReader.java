package com.baeldung.jimfs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

class FilePathReader {
    String getSystemPath(Path path) {
        try {
            return path
              .toRealPath()
              .toString();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
