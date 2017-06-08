package com.baeldung.disetter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.disetter.model.Bike;
import com.baeldung.disetter.model.Engine;
import com.baeldung.disetter.model.Tyre;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  loader=AnnotationConfigContextLoader.class,
  classes=SpringConfig.class)
public class SetterAutowiredIntegrationTest {
    @Autowired
    private Bike bike;
    
    @Autowired
    private Engine engine;
    
    @Autowired
    private Tyre tyre;
    
    @Test
    public void givenAutowired_WhenSetOnField_ThenDependencyResolved() {
        assertNotNull(engine);
        assertNotNull(tyre);
        assertNotNull(bike);
        assertEquals("Engine of type Internal Combustion Engine and displacement 150 cc.", engine.toString());
        assertEquals("Tyres of brand Bridgestone and size 165/81", tyre.toString());
        assertEquals("Bike characteristics:\nEngine of type Internal Combustion Engine and displacement 150 cc.\nTyres of brand Bridgestone and size 165/81", bike.toString());
    }
}
