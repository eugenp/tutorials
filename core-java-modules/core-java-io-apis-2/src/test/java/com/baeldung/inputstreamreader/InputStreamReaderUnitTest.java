package com.baeldung.inputstreamreader;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class InputStreamReaderUnitTest {
    @Test
    public void givenAStringWrittenToAFile_whenReadByInputStreamReader_thenShouldMatchWhenRead(@TempDir Path tempDir) throws IOException {
        String sampleTxt = "Good day. This is just a test. Good bye.";
        Path sampleOut = tempDir.resolve("sample-out.txt");
        List<String> lines = Arrays.asList(sampleTxt);
        Files.write(sampleOut, lines);
        String absolutePath = String.valueOf(sampleOut.toAbsolutePath());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath), StandardCharsets.UTF_8))) {
            String ln;
            boolean isMatched = false;
            while ((ln = br.readLine()) != null) {
                if (ln.contains(sampleTxt)) {
                    isMatched = true;
                    break;
                }
            }
            assertThat(isMatched).isTrue();
        }
    }
}
