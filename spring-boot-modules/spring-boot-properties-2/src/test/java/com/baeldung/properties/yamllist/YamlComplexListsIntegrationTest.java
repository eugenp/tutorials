package com.baeldung.properties.yamllist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.properties.yamllist.pojo.ApplicationProps;

@RunWith(SpringRunner.class)
@SpringBootTest
class YamlComplexListsIntegrationTest {

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
