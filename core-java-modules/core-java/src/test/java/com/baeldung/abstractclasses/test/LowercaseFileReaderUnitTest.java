package com.baeldung.abstractclasses.test;

import com.baeldung.abstractclasses.filereaders.BaseFileReader;
import com.baeldung.abstractclasses.filereaders.LowercaseFileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class LowercaseFileReaderUnitTest {
    
    @Test
    public void givenLowercaseFileReaderInstance_whenCalledreadFile_thenCorrect() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("files/test.txt").toURI());
        BaseFileReader lowercaseFileReader = new LowercaseFileReader(path);
        
        assertThat(lowercaseFileReader.readFile()).isInstanceOf(List.class);
    }
}
