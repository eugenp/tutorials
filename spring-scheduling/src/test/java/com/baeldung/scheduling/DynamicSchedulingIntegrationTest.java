package com.baeldung.scheduling;

import com.baeldung.scheduling.dynamic.DynamicSchedulingConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
  DynamicSchedulingConfig.class}, loader = AnnotationConfigContextLoader.class)
public class DynamicSchedulingIntegrationTest {

    @Test
    public void testTickServiceTick() throws InterruptedException {
        Thread.sleep(6000);
    }
}
