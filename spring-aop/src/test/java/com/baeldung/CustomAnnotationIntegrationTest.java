package com.baeldung;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomAnnotationIntegrationTest {

    @Autowired
    private Service service;

    @Test
    public void shouldApplyCustomAnnotation() throws InterruptedException {
        assertThat(service).isNotNull();
        service.serve();
    }

}
