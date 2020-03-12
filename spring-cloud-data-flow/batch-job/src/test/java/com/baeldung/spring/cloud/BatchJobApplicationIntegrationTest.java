package com.baeldung.spring.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = JobConfiguration.class)
public class BatchJobApplicationIntegrationTest {

    @Test
    public void contextLoads() {
    }

}
