package com.baeldung.copyfiles;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class FileCopierTest {
    File original =new File("src/test/resources/original.txt");
    @Before
    public void init() throws IOException{
        if(!original.exists())
            Files.createFile(original.toPath());
    }
    @Test
    public void givenIoAPI_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        File copied = new File("src/test/resources/copiedWithIo.txt");
        copied = FileCopier.copyWithIO(original, copied);
        assertTrue(copied.exists());
        assertTrue(Files.readAllLines(original.toPath())
            .equals(Files.readAllLines(copied.toPath())));
    }

    @Test
    public void givenCommonsIoAPI_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        File copied = new File("src/test/resources/copiedWithApacheCommons.txt");
        copied = FileCopier.copyWithCommonsIO(original, copied);
        assertTrue(copied.exists());
        assertTrue(Files.readAllLines(original.toPath())
            .equals(Files.readAllLines(copied.toPath())));
    }

    @Test
    public void givenNIO2_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        Path copied = Paths.get("src/test/resources/copiedWithNio.txt");
        Path originalPath = original.toPath();
        copied = FileCopier.copyWithNio(originalPath, copied);
        assertTrue(Files.exists(copied));
        assertTrue(Files.readAllLines(originalPath)
            .equals(Files.readAllLines(copied)));
    }
}
