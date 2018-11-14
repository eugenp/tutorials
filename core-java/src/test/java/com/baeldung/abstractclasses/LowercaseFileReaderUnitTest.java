package com.baeldung.abstractclasses;

import com.baeldung.abstractclasses.filereaders.BaseFileReader;
import com.baeldung.abstractclasses.filereaders.LowercaseFileReader;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class LowercaseFileReaderUnitTest {

    @Test
    public void givenLowercaseFileReaderInstance_whenCalledreadFile_thenCorrect() throws Exception {
        // We'll transform the resource URL path to URI to load the file correctly in Windows
        URL url = getClass().getClassLoader().getResource("files/test.txt");
        String filePath = Paths.get(url.toURI()).toString();
        BaseFileReader lowercaseFileReader = new LowercaseFileReader(filePath);

        assertThat(lowercaseFileReader.readFile()).isInstanceOf(List.class);
    }
}
