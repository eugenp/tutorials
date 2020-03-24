package com.baeldung.copyfiles;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class FileCopierIntegrationTest {
    File original = new File("src/test/resources/original.txt");

    @Before
    public void init() throws IOException {
        if (!original.exists())
            Files.createFile(original.toPath());
    }

    @Test
    public void givenIoAPI_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        File copied = new File("src/test/resources/copiedWithIo.txt");
        try (InputStream in = new BufferedInputStream(new FileInputStream(original)); OutputStream out = new BufferedOutputStream(new FileOutputStream(copied))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
        assertThat(copied).exists();
        assertThat(Files.readAllLines(original.toPath()).equals(Files.readAllLines(copied.toPath())));
    }

    @Test
    public void givenCommonsIoAPI_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        File copied = new File("src/test/resources/copiedWithApacheCommons.txt");
        FileUtils.copyFile(original, copied);
        assertThat(copied).exists();
        assertThat(Files.readAllLines(original.toPath()).equals(Files.readAllLines(copied.toPath())));
    }

    @Test
    public void givenNIO2_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        Path copied = Paths.get("src/test/resources/copiedWithNio.txt");
        Path originalPath = original.toPath();
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        assertThat(copied).exists();
        assertThat(Files.readAllLines(originalPath).equals(Files.readAllLines(copied)));
    }

    @Test
    public void givenGuava_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        File copied = new File("src/test/resources/copiedWithApacheCommons.txt");
        com.google.common.io.Files.copy(original, copied);
        assertThat(copied).exists();
        assertThat(Files.readAllLines(original.toPath()).equals(Files.readAllLines(copied.toPath())));
    }
}
