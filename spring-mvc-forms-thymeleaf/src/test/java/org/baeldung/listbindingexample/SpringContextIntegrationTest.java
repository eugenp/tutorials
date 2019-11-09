package org.baeldung.listbindingexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.listbindingexample.ListBindingApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ListBindingApplication.class})
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
