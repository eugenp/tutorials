package com.baeldung.injectiontypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = Config.class)
public class DoctorTest {
    @Autowired
    private Doctor doctor;

    @Test
    public void givenDoctor_whenInitialised_thenHospitalSet() throws Exception {
        assertNotNull(doctor.getHospital());
    }
}