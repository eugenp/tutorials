package com.baeldung.commons.untar;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Test;

import com.baeldung.commons.untar.impl.TarExtractorVfs;

public class TarExtractorVfsUnitTest {

    @Test
    public void givenTarFile_whenUntar_thenExtractedToDestination() throws IOException {
        Path destination = Paths.get("/tmp/vfs");

        new TarExtractorVfs(Resources.tarFile(), false, destination).untar();

        try (Stream<Path> files = Files.list(destination)) {
            assertTrue(files.findFirst()
                .isPresent());
        }
    }

    @Test
    public void givenTarGzFile_whenUntar_thenExtractedToDestination() throws IOException {
        Path destination = Paths.get("/tmp/vfs-gz");

        new TarExtractorVfs(Resources.tarGzFile(), true, destination).untar();

        try (Stream<Path> files = Files.list(destination)) {
            assertTrue(files.findFirst()
                .isPresent());
        }
    }
}