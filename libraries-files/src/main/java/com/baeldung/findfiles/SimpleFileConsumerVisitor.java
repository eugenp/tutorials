package com.baeldung.findfiles;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SimpleFileConsumerVisitor extends SimpleFileVisitor<Path> {

    private final Predicate<Path> filter;
    private final Consumer<Path> consumer;

    public SimpleFileConsumerVisitor(MatchExtensionPredicate filter, Consumer<Path> consumer) {
        this.filter = filter;
        this.consumer = consumer;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
        if (filter.test(file))
            consumer.accept(file);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
