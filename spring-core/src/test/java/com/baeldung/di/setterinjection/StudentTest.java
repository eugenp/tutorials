package com.baeldung.di.setterinjection;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class StudentTest {

    @Autowired
    private Student kim;
    
    @Test
    public void givenConstructorInjection_thenSuccessfulBeanCreation() {
        assertNotNull(kim);
        assertEquals(18, kim.getPersonalDetails().getAge());
        assertEquals("Kim", kim.getPersonalDetails().getName());
        assertEquals("Chem001", kim.getPersonalDetails().getId());
        assertEquals("Chemistry", kim.getDepartment().getName());
    }

}
