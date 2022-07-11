package com.baeldung.streamutils;

import org.junit.Test;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.baeldung.streamutils.CopyStream.getStringFromInputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CopyStreamIntegrationTest {

    @Test
    public void whenCopyInputStreamToOutputStream_thenCorrect() throws IOException {
        Path inputFile = Paths.get("src/test/resources/input.txt");
        Path outputFile = Paths.get("src/test/resources/output.txt");
        InputStream in = Files.newInputStream(inputFile);
        OutputStream out = Files.newOutputStream(outputFile);

        StreamUtils.copy(in, out);

        assertTrue(Files.exists(outputFile));

        String inputFileContent = getStringFromInputStream(Files.newInputStream(inputFile));
        String outputFileContent = getStringFromInputStream(Files.newInputStream(outputFile));
        assertEquals(inputFileContent, outputFileContent);
    }

    @Test
    public void whenCopyRangeOfInputStreamToOutputStream_thenCorrect() throws IOException {
        Path inputFile = Paths.get("src/test/resources/input.txt");
        Path outputFile = Paths.get("src/test/resources/output.txt");
        InputStream in = Files.newInputStream(inputFile);
        OutputStream out = Files.newOutputStream(outputFile);

        StreamUtils.copyRange(in, out, 1, 10);

        assertTrue(Files.exists(outputFile));

        String inputFileContent = getStringFromInputStream(Files.newInputStream(inputFile));
        String outputFileContent = getStringFromInputStream(Files.newInputStream(outputFile));
        assertEquals(inputFileContent.substring(1, 11), outputFileContent);
    }

    @Test
    public void whenCopyStringToOutputStream_thenCorrect() throws IOException {
        String string = "Should be copied to OutputStream.";
        String outputFileName = "src/test/resources/output.txt";
        Path outputFile = Paths.get("src/test/resources/output.txt");
        OutputStream outputStream = Files.newOutputStream(outputFile);

        StreamUtils.copy(string, StandardCharsets.UTF_8, outputStream);

        assertTrue(Files.exists(outputFile));

        String outputFileContent = getStringFromInputStream(Files.newInputStream(Paths.get(outputFileName)));
        assertEquals(outputFileContent, string);
    }

    @Test
    public void whenCopyInputStreamToString_thenCorrect() throws IOException {
        Path inputFile = Paths.get("src/test/resources/input.txt");
        InputStream inputStream = Files.newInputStream(inputFile);

        String content = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        String inputFileContent = getStringFromInputStream(Files.newInputStream(inputFile));
        assertEquals(inputFileContent, content);
    }

    @Test
    public void whenCopyByteArrayToOutputStream_thenCorrect() throws IOException {
        Path outputFile = Paths.get("src/test/resources/output.txt");
        String string = "Should be copied to OutputStream.";
        byte[] byteArray = string.getBytes();
        OutputStream out = Files.newOutputStream(outputFile);

        StreamUtils.copy(byteArray, out);

        String outputFileContent = getStringFromInputStream(Files.newInputStream(outputFile));
        assertEquals(outputFileContent, string);
    }

    @Test
    public void whenCopyInputStreamToByteArray_thenCorrect() throws IOException {
        Path inputFile = Paths.get("src/test/resources/input.txt");
        InputStream in = Files.newInputStream(inputFile);

        byte[] out = StreamUtils.copyToByteArray(in);

        String content = new String(out);
        String inputFileContent = getStringFromInputStream(Files.newInputStream(inputFile));
        assertEquals(inputFileContent, content);
    }

}
