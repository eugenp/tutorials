package com.baeldung.runners;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

@ContextConfiguration(classes = StringConverter.class)
@RunWith(SpringRunner.class)
public class StringConverterSpringRunnerUnitTest {
    @MockBean
    private DataProvider dataProvider;

    @Autowired
    private StringConverter stringConverter;

    @Test
    public void givenStrings_whenConvert_thenReturnUpperCase() {
        Mockito.when(dataProvider.getValues()).thenReturn(Stream.of("first", "second"));

        val result = stringConverter.convert();

        Assertions.assertThat(result).contains("FIRST", "SECOND");
    }
}