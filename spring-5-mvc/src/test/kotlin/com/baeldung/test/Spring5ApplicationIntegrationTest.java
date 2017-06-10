package com.baeldung.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.Spring5Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5Application.class)
public class Spring5ApplicationIntegrationTest {

    @Test
    public void contextLoads() {
    }

}
