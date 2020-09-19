package com.baeldung.properties.yamlmap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.properties.yamlmap.pojo.ServerProperties;

@RunWith(SpringRunner.class)
@SpringBootTest
class MapFromYamlIntegrationTest {

    @Autowired
    private ServerProperties serverProperties;

    @Test
    public void whenYamlFileProvidedThenInjectSimpleMap() {

        assertThat(serverProperties.getApplication()).containsOnlyKeys("name", "url", "description");

        assertThat(serverProperties.getApplication()
            .get("name")).isEqualTo("InjectMapFromYAML");
    }

    @Test
    public void whenYamlFileProvidedThenInjectComplexMap() {

        assertThat(serverProperties.getConfig()).hasSize(2);

        assertThat(serverProperties.getConfig()
            .get("ips")
            .get(0)).isEqualTo("10.10.10.10");

        assertThat(serverProperties.getUsers()
            .get("root")
            .getUsername()).isEqualTo("root");
    }

}
