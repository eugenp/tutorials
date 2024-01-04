package com.baeldung.findfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class FindFileJava8Utils {

    private FindFileJava8Utils() {
    }

    public static void find(Path startPath, String extension, Consumer<Path> consumer) throws IOException {
        if (!Files.isDirectory(startPath)) {
            throw new IllegalArgumentException("Provided path is not a directory: " + startPath);
        }

        MatchExtensionPredicate filter = new MatchExtensionPredicate(extension);
        Files.walkFileTree(startPath, new SimpleFileConsumerVisitor(filter, consumer));
    }
}
