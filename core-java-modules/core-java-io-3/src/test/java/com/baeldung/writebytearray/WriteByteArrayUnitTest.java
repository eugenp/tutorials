package com.baeldung.writebytearray;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.io.ByteSink;
import com.google.common.io.MoreFiles;

public class WriteByteArrayUnitTest {
    private static byte[] dataForWriting;

    @BeforeClass
    public static void setup() throws IOException {
        dataForWriting = Files.readAllBytes(Paths.get("src/test/resources/example-image.jpg"));
    }

    @Test
    public void whenUsingFileOutputStream_thenByteArrayIsWritten() throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream("src/test/resources/example-fos.jpg")) {
            outputStream.write(dataForWriting);
            assertThat(new File("src/test/resources/example-fos.jpg")).hasBinaryContent(dataForWriting);
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    @Test
    public void whenUsingNioFiles_thenByteArrayIsWritten() throws IOException {
        // Takes Optional OpenOptions, if not present defaults to CREATE, TRUNCATE_EXISTING (will truncate an existing file to size of 0), and WRITE
        // https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#write-java.nio.file.Path-byte:A-java.nio.file.OpenOption...-
        Files.write(Paths.get("src/test/resources/example-nio-files.jpg"), dataForWriting);
        assertThat(new File("src/test/resources/example-nio-files.jpg")).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingGuavaFiles_thenByteArrayIsWritten() throws IOException {
        com.google.common.io.Files.write(dataForWriting, new File("src/test/resources/example-guava-files.jpg"));
        assertThat(new File("src/test/resources/example-guava-files.jpg")).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingGuavaByteSink_thenByteArrayIsWritten() throws IOException {
        // Also discuss - https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/io/MoreFiles.html
        ByteSink byteSink = com.google.common.io.Files.asByteSink(new File("src/test/resources/example-guava-bs.jpg"));
        byteSink.write(dataForWriting);
        assertThat(new File("src/test/resources/example-guava-bs.jpg")).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingGuavaByteSinkMoreFiles_thenByteArrayIsWritten() throws IOException {
        ByteSink byteSink = MoreFiles.asByteSink(Paths.get("src/test/resources/example-guava-bs-mf.jpg"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        byteSink.write(dataForWriting);
        assertThat(new File("src/test/resources/example-guava-bs-mf.jpg")).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUserCommonsIo_thenByteArrayIsWritten() throws IOException {
        // http://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/FileUtils.html#writeByteArrayToFile%28java.io.File,%20byte%5B%5D%29
        FileUtils.writeByteArrayToFile(new File("src/test/resources/example-file-utils.jpg"), dataForWriting);
        assertThat(new File("src/test/resources/example-file-utils.jpg")).hasBinaryContent(dataForWriting);
    }

    @AfterClass
    public static void teardown() throws IOException {
        Files.deleteIfExists(Paths.get("src/test/resources/example-fos.jpg"));
        Files.deleteIfExists(Paths.get("src/test/resources/example-nio-files.jpg"));
        Files.deleteIfExists(Paths.get("src/test/resources/example-guava-files.jpg"));
        Files.deleteIfExists(Paths.get("src/test/resources/example-guava-bs.jpg"));
        Files.deleteIfExists(Paths.get("src/test/resources/example-guava-bs-mf.jpg"));
        Files.deleteIfExists(Paths.get("src/test/resources/example-file-utils.jpg"));
    }
}
