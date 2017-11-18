package com.baeldung.jupiter;

import com.baeldung.web.reactive.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class Spring5JUnit5IntegrationTest {

    @Autowired
    private Task task;

    @Test
    void givenAMethodName_whenInjecting_thenApplicationContextInjectedIntoMetho(ApplicationContext applicationContext) {
        assertNotNull(applicationContext, "ApplicationContext should have been injected by Spring");
        assertEquals(this.task, applicationContext.getBean("taskName", Task.class));
    }
}
