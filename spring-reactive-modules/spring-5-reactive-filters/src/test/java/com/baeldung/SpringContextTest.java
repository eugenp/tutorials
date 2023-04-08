package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.reactive.Spring5ReactiveFiltersApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5ReactiveFiltersApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
