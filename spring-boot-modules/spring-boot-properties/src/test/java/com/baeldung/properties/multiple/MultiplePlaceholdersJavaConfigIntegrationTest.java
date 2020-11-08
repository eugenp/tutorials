package com.baeldung.properties.multiple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.baeldung.properties.spring.PropertyPlaceholderConfig;
import com.baeldung.properties.spring.PropertySourcesPlaceholderConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig({PropertyPlaceholderConfig.class, PropertySourcesPlaceholderConfig.class})
public class MultiplePlaceholdersJavaConfigIntegrationTest {

    @Value("${key.something}")
    private String something;


    @Test
    public void whenReadInjectedValues_thenGetCorrectValues() {
        assertThat(something).isEqualTo("val");
    }
}