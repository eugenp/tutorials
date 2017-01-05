package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
public class DataLoaderTest {

    @Test
    public void exampleTest() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:8080/startNotification/10", String.class);
    }

}
