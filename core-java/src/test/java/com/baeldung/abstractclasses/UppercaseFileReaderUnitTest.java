package com.baeldung.abstractclasses;

import com.baeldung.abstractclasses.filereaders.BaseFileReader;
import com.baeldung.abstractclasses.filereaders.UppercaseFileReader;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class UppercaseFileReaderUnitTest {

    @Test
    public void givenUppercaseFileReaderInstance_whenCalledreadFile_thenCorrect() throws Exception {
        // We'll transform the resource URL path to URI to load the file correctly in Windows 
        URL url = getClass().getClassLoader().getResource("files/test.txt");
        String filePath = Paths.get(url.toURI()).toString();
        BaseFileReader uppercaseFileReader = new UppercaseFileReader(filePath);
        
        assertThat(uppercaseFileReader.readFile()).isInstanceOf(List.class);
    }
}
