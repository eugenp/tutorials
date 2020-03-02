package com.baeldung.buildproperties;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class BuildInfoServiceIntegrationTest {

    @Autowired
    private BuildInfoService service;
    
    @Test
    void whenGetApplicationDescription_thenSuccess() {
        assertThat(service.getApplicationDescription(), Matchers.is("This is simple boot application for Spring boot actuator test"));
        assertThat(service.getApplicationVersion(), Matchers.is("0.0.1-SNAPSHOT"));
    }
}
