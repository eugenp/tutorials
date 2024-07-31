package com.baeldung;

import com.baeldung.spring.cloud.TimeProcessorApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimeProcessorApplication.class)
public class SpringContextTest {

    @Test
    public void contextLoads() {
    }

}
