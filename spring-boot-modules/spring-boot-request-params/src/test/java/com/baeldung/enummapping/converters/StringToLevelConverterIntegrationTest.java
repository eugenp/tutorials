package com.baeldung.enummapping.converters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.enummapping.EnumMappingMainApplication;
import com.baeldung.enummapping.enums.Level;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnumMappingMainApplication.class)
public class StringToLevelConverterIntegrationTest {

    @Autowired
    ConversionService conversionService;

    @Test
    public void whenConvertStringToLevelEnumUsingCustomConverter_thenSuccess() {
        assertThat(conversionService.convert("low", Level.class)).isEqualTo(Level.LOW);
    }

    @Test
    public void whenStringIsEmpty_thenReturnNull() {
        assertThat(conversionService.convert("", Level.class)).isNull();
    }

    @Test
    public void whenStringIsNull_thenReturnNull() {
        assertThat(conversionService.convert(null, Level.class)).isNull();
    }

}
