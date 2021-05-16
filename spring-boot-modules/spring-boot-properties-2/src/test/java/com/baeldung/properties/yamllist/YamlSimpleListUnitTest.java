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
class YamlSimpleListUnitTest {

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
