package com.baeldung.scheduling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringSchedulingFixedRateConfig.class }, loader = AnnotationConfigContextLoader.class)
public class ScheduledFixedRateExampleIntegrationTest {

    @Test
    public void testScheduledFixedRateAnnotation() throws InterruptedException {
        Thread.sleep(5000);
    }
}
