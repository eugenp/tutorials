package com.baeldung.jupiter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnit5Config(TestConfig.class)
@DisplayName("@SpringJUnit5Config Tests")
class Spring5JUnit5ComposedAnnotationIntegrationTest {

    @Autowired
    private Task task;

    @Autowired
    private List<Task> tasks;

    @Test
    @DisplayName("ApplicationContext injected into method")
    void givenAMethodName_whenInjecting_thenApplicationContextInjectedIntoMethod(ApplicationContext applicationContext) {
        assertNotNull(applicationContext, "ApplicationContext should have been injected into method by Spring");
        assertEquals(this.task, applicationContext.getBean("taskName", Task.class));
    }

    @Test
    @DisplayName("Spring @Beans injected into fields")
    void givenAnObject_whenInjecting_thenSpringBeansInjected() {
        assertNotNull(task, "Task should have been @Autowired by Spring");
        assertEquals("taskName", task.getName(), "Task's name");
        assertEquals(1, tasks.size(), "Number of Tasks in context");
    }
}
