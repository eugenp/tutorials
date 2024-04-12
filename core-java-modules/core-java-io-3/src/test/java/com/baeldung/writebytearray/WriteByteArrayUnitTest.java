package com.baeldung.writebytearray;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.io.ByteSink;
import com.google.common.io.MoreFiles;

public class WriteByteArrayUnitTest {
    private static byte[] dataForWriting;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setup() throws IOException {
        dataForWriting = Files.readAllBytes(Paths.get("src/test/resources/example-image.jpg"));
    }

    @Test
    public void whenUsingFileOutputStream_thenByteArrayIsWritten() throws IOException {
        File outputFile = tempFolder.newFile("example-fos.jpg");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(dataForWriting);
            assertThat(outputFile).hasBinaryContent(dataForWriting);
        }
    }

    @Test
    public void whenUsingNioFiles_thenByteArrayIsWritten() throws IOException {
        File outputFile = tempFolder.newFile("example-nio-files.jpg");
        Files.write(outputFile.toPath(), dataForWriting);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingGuavaFiles_thenByteArrayIsWritten() throws IOException {
        File outputFile = tempFolder.newFile("example-guava-files.jpg");
        com.google.common.io.Files.write(dataForWriting, outputFile);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingGuavaByteSink_thenByteArrayIsWritten() throws IOException {
        File outputFile = tempFolder.newFile("example-guava-bs.jpg");
        ByteSink byteSink = com.google.common.io.Files.asByteSink(outputFile);
        byteSink.write(dataForWriting);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingGuavaByteSinkMoreFiles_thenByteArrayIsWritten() throws IOException {
        File outputFile = tempFolder.newFile("example-guava-bs.jpg");
        ByteSink byteSink = MoreFiles.asByteSink(outputFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        byteSink.write(dataForWriting);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingCommonsIo_thenByteArrayIsWritten() throws IOException {
        File outputFile = tempFolder.newFile("example-file-utils.jpg");
        FileUtils.writeByteArrayToFile(outputFile, dataForWriting);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }
}
