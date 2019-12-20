package com.baeldung.taskscheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ThreadPoolTaskSchedulerConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ThreadPoolTaskSchedulerIntegrationTest {

    @Test
    public void testThreadPoolTaskSchedulerAnnotation() throws InterruptedException {
        Thread.sleep(2550);
    }
}