package org.baeldung;

import org.baeldung.config.UiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringContextIntegrationTest {

    @Test
    public void whenLoadApplication_thenSuccess() {

    }
}
