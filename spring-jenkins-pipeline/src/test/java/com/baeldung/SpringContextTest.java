package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.SpringJenkinsPipelineApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringJenkinsPipelineApplication.class)
@DirtiesContext
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
