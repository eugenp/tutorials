package com.baeldung.creationdate;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;

import org.junit.Test;

public class CreationDateResolverUnitTest {

    private final CreationDateResolver creationDateResolver = new CreationDateResolver();

    @Test
    public void givenFile_whenGettingCreationDateTimeFromBasicAttributes_thenReturnDate() throws Exception {

        final File file = File.createTempFile("createdFile", ".txt");
        final Path path = file.toPath();

        final Instant response = creationDateResolver.resolveCreationTimeWithBasicAttributes(path);

        Optional.of(response).ifPresent((value) -> {
            assertTrue(Instant
              .now()
              .isAfter(value));
        });

    }

    @Test
    public void givenFile_whenGettingCreationDateTimeFromAttribute_thenReturnDate() throws Exception {

        final File file = File.createTempFile("createdFile", ".txt");
        final Path path = file.toPath();

        final Optional<Instant> response = creationDateResolver.resolveCreationTimeWithAttribute(path);

        response.ifPresent((value) -> {
            assertTrue(Instant
              .now()
              .isAfter(value));
        });

    }
}
