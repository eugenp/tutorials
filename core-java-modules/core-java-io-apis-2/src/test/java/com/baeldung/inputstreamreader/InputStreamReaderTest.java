package com.baeldung.inputstreamreader;

import static org.junit.jupiter.api.io.CleanupMode.ALWAYS;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class InputStreamReaderTest {
    private Path sharedTempDir = null;

    @Test
    public void givenAString_whenReadByInputStreamReader_thenShouldMatchWhenRead(@TempDir(cleanup = ALWAYS) Path tempDir) throws IOException {
        boolean isMatched = false;
        String sampleTxt = "Good day. This is just a test. Good bye.";
        sharedTempDir = tempDir;
        Path sampleOut = sharedTempDir.resolve("sample-out.txt");
        List<String> lines = Arrays.asList(sampleTxt);
        //create and write file
        Files.write(sampleOut, lines);
        try( FileInputStream fis = new FileInputStream(sampleOut.toFile());){
            try( BufferedReader br
                = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));){
                String ln;
                while((ln = br.readLine())!= null){
                    System.out.println(ln);
                    if(lines.contains(sampleTxt)){
                        isMatched = true;
                        break;
                    }
                }
                Assert.assertTrue(isMatched);
            }

        }
    }

}
