package com.baeldung.findfiles;

import java.nio.file.Path;
import java.util.function.Predicate;

public class MatchExtensionPredicate implements Predicate<Path> {

    private final String extension;

    public MatchExtensionPredicate(String extension) {
        if (!extension.startsWith("."))
            extension = "." + extension;
        this.extension = extension.toLowerCase();
    }

    @Override
    public boolean test(Path path) {
        if (path == null)
            return false;

        return path.getFileName()
            .toString()
            .toLowerCase()
            .endsWith(extension);
    }
}
