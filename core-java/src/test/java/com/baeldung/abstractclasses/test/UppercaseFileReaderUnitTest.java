package com.baeldung.abstractclasses.test;

import com.baeldung.abstractclasses.filereaders.BaseFileReader;
import com.baeldung.abstractclasses.filereaders.LowercaseFileReader;
import com.baeldung.abstractclasses.filereaders.UppercaseFileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class UppercaseFileReaderUnitTest {

    @Test
    public void givenUppercaseFileReaderInstance_whenCalledreadFile_thenCorrect() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("files/test.txt").toURI());
        BaseFileReader lowercaseFileReader = new LowercaseFileReader(path);
        
        assertThat(lowercaseFileReader.readFile()).isInstanceOf(List.class);
    }
    
    @Test
    public void givenLowercaseFileReaderInstance_whenCalledMapFileLines_thenCorrect() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("files/test.txt").toURI());
        BaseFileReader upperBaseFileReader = new UppercaseFileReader(path);
        
        assertThat(upperBaseFileReader.mapFileLines().get(0)).isEqualTo("THIS IS LINE 1");
    }
}
