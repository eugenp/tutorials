package com.baeldung.inputstreamreader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
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

    @Test
    public void givenAStringWrittenToAFile_whenReadByInputStreamReader_thenShouldMatchWhenRead(@TempDir Path tempDir) throws IOException {
        boolean isMatched = false;
        String sampleTxt = "Good day. This is just a test. Good bye.";
        Path sampleOut = tempDir.resolve("sample-out.txt");
        List<String> lines = Arrays.asList(sampleTxt);
        //create and write file
        Files.write(sampleOut, lines);
        try(FileInputStream fis = new FileInputStream(String.valueOf(sampleOut.toAbsolutePath()));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));){
            String ln;
            while((ln = br.readLine())!= null){
                if(ln.contains(sampleTxt)){
                    isMatched = true;
                    break;
                }
            }
            Assert.assertTrue(isMatched);
        }
    }

}
