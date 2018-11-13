package com.baeldung.abstractclasses;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import com.baeldung.abstractclasses.filereaders.BaseFileReader;
import com.baeldung.abstractclasses.filereaders.StandardFileReader;

public class StandardFileReaderUnitTest {

    @Test
    public void givenStandardFileReaderInstance_whenCalledreadFile_thenCorrect() throws Exception {
        // We'll transform the resource URL path to URI to load the file correctly in Windows 
        URL url = getClass().getClassLoader().getResource("files/test.txt");
        String filePath = Paths.get(url.toURI()).toString();
        BaseFileReader standardFileReader = new StandardFileReader(filePath);
        
        assertThat(standardFileReader.readFile()).isInstanceOf(List.class);
    }
}
