package com.baeldung.runners;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class StringConverterMockitoRunnerUnitTest {
    @Mock
    private DataProvider dataProvider;

    @InjectMocks
    private StringConverter stringConverter;

    @Test
    public void givenStrings_whenConvert_thenReturnUpperCase() {
        Mockito.when(dataProvider.getValues()).thenReturn(Stream.of("first", "second"));

        val result = stringConverter.convert();

        Assertions.assertThat(result).contains("FIRST", "SECOND");
    }
}