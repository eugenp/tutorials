package com.java.baeldung.transientkw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class TransientUnitTest {

    @Test
    void givenTransient_whenSerDe_thenVerifyValues() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Connor");
        person.setMiddleName("Will not be saved");
        person.setAge(25);
        
        PersonSerDe.serialize(person);
        Person person2 = PersonSerDe.deserialize();
        
        assertEquals("John", person2.getFirstName());
        assertNull(person2.getMiddleName());
        assertEquals(0, person2.getAge());
    }
    
    @Test
    void givenFinalTransient_whenSerDe_thenValuePersisted() {
        Person person = new Person();
        
        PersonSerDe.serialize(person);
        Person person2 = PersonSerDe.deserialize();
        
        assertEquals("CS", person2.getMajor());
        
    }

}
