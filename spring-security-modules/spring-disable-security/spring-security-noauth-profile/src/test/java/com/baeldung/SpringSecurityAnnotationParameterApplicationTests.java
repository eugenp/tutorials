package com.baeldung;


import com.baeldung.SpringSecurityAnnotationParameterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSecurityAnnotationParameterApplication.class)
public class SpringSecurityAnnotationParameterApplicationTests {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}

