package com.baeldung.properties.multiple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;


@SpringJUnitConfig(locations = "classpath:configForPropertyPlaceholderBeans.xml")
public class MultiplePlaceholdersXmlConfigIntegrationTest {

    @Value("${foo}")
    private String something;

    @Value("${key.something}")
    private String something2;


    @Test
    public void whenReadInjectedValues_thenGetCorrectValues() {
        assertThat(something).isEqualTo("bar");
        assertThat(something2).isEqualTo("val");
    }
}