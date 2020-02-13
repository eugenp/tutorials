package com.baeldung.properties.multiple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = "classpath:configForProperties.xml")
public class MultiplePropertiesXmlConfigIntegrationTest {

    @Value("${key.something}") private String something;

    @Value("${key.something2}") private String something2;

    @Test
    public void whenReadInjectedValues_thenGetCorrectValues() {
        assertThat(something).isEqualTo("val");
        assertThat(something2).isEqualTo("val2");
    }
}
