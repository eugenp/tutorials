package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.eclipselink.springdata.EclipselinkSpringDataApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EclipselinkSpringDataApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
