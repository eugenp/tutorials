package com.baeldung.abstractclasses;

import com.baeldung.abstractclasses.filereaders.BaseFileReader;
import com.baeldung.abstractclasses.filereaders.LowercaseFileReader;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class LowercaseFileReaderUnitTest {
    
    @Test
    public void givenLowercaseFileReaderInstance_whenCalledreadFile_thenCorrect() throws Exception {
        String filePath = getClass().getClassLoader().getResource("files/test.txt").getPath().substring(1);
        BaseFileReader lowercaseFileReader = new LowercaseFileReader(filePath);
        
        assertThat(lowercaseFileReader.readFile()).isInstanceOf(List.class);
    }
}
