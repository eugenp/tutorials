package com.baeldung.springbootxml;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootXmlApplication.class)
public class SpringBootXmlApplicationIntegrationTest {

    @Autowired private Pojo pojo;
    @Value("${sample}") private String sample;

    @Test
    public void whenCallingGetter_thenPrintingProperty() {
        assertThat(pojo.getField())
                .isNotBlank()
                .isEqualTo(sample);
    }

}