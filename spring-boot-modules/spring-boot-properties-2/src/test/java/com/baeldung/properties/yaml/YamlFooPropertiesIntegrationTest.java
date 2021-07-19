package com.baeldung.properties.yaml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YamlFooPropertiesIntegrationTest {

    @Autowired
    private YamlFooProperties yamlFooProperties;

    @Test
    public void whenFactoryProvidedThenYamlPropertiesInjected() {
        assertThat(yamlFooProperties.getName()).isEqualTo("foo");
        assertThat(yamlFooProperties.getAliases()).containsExactly("abc", "xyz");
    }
}