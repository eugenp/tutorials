package com.baeldung.injectiontypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = Config.class)
public class SurgeonTest {
    @Autowired
    private Surgeon surgeon;

    @Test
    public void givenSurgeon_whenInitialised_thenHospitalSet() throws Exception {
        assertNotNull(surgeon.getHospital());
    }
}