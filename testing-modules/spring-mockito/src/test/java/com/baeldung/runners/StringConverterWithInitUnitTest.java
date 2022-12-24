package com.baeldung.runners;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

public class StringConverterWithInitUnitTest {
    @Mock
    private DataProvider dataProvider;

    @InjectMocks
    private StringConverter stringConverter;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        // or
        // dataProvider = Mockito.mock(DataProvider.class);
        // stringConverter = new StringConverter(dataProvider);
    }

    @Test
    public void givenStrings_whenConvert_thenReturnUpperCase() {
        Mockito.when(dataProvider.getValues()).thenReturn(Stream.of("first", "second"));

        val result = stringConverter.convert();

        Assertions.assertThat(result).contains("FIRST", "SECOND");
    }
}