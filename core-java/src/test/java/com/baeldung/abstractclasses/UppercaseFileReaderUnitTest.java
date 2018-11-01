package com.baeldung.abstractclasses;

import com.baeldung.abstractclasses.filereaders.BaseFileReader;
import com.baeldung.abstractclasses.filereaders.UppercaseFileReader;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class UppercaseFileReaderUnitTest {

    @Test
    public void givenStandardFileReaderInstance_whenCalledreadFile_thenCorrect() throws Exception {
        String filePath = getClass().getClassLoader().getResource("files/test.txt").getPath().substring(1);
        BaseFileReader uppercaseFileReader = new UppercaseFileReader(filePath);
        
        assertThat(uppercaseFileReader.readFile()).isInstanceOf(List.class);
    }
}
