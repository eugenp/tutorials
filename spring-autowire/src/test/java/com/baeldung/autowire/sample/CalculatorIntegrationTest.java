package com.baeldung.autowire.sample;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class CalculatorIntegrationTest {

    @Autowired
    CalcService service;

    @Test
    public void whenInput2_thenReturn4() {
        Assert.assertEquals(4, service.doubled(2));
    }
}
