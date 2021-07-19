package com.baeldung.di.aspectj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AspectJConfig.class)
public class PersonUnitTest {
    @Test
    public void givenUnmanagedObjects_whenInjectingIdService_thenIdValueIsCorrectlySet() {
        PersonObject personObject = new PersonObject("Baeldung");
        personObject.generateId();
        assertEquals(1, personObject.getId());
        assertEquals("Baeldung", personObject.getName());

        PersonEntity personEntity = new PersonEntity("Baeldung");
        assertEquals(2, personEntity.getId());
        assertEquals("Baeldung", personEntity.getName());
    }
}