package com.baeldung.properties.yamllist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.properties.yamllist.pojo.ApplicationProps;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ApplicationProps.class)
class YamlComplexListsUnitTest {

    @Autowired
    private ApplicationProps applicationProps;

    @Test
    public void whenYamlNestedLists_thenLoadComplexLists() {
        assertThat(applicationProps.getUsers()
            .get(0)
            .getPassword()).isEqualTo("admin@10@");
        assertThat(applicationProps.getProps()
            .get(0)
            .get("name")).isEqualTo("YamlList");
        assertThat(applicationProps.getProps()
            .get(1)
            .get("port")
            .getClass()).isEqualTo(Integer.class);
    }

}
