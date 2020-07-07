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
class YamlSimpleListIntegrationTest {

    @Autowired
    private ApplicationProps applicationProps;

    @Test
    public void whenYamlList_thenLoadSimpleList() {
        assertThat(applicationProps.getProfiles()
            .get(0)).isEqualTo("dev");
        assertThat(applicationProps.getProfiles()
            .get(4)
            .getClass()).isEqualTo(Integer.class);
        assertThat(applicationProps.getProfiles()
            .size()).isEqualTo(5);
    }
}
