package com.baeldung.evaluation.model.builder;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.baeldung.evaluation.model.Address;
import com.baeldung.evaluation.model.Person;


public class PersonCreatorTest {
    
    PersonCreator personCreator = new PersonCreator();
    
    Person originalPerson;
    
    @Before
    void setup() {
        originalPerson = new Person();
        originalPerson.setAge(15);
        originalPerson.setAddress(new Address());
        originalPerson.getAddress().setCountry("Brazil");    
        
    }

    @Test
    void testCloneInformation() throws CloneNotSupportedException {    
        
        // clone usage, change the existing value
        Person clonedPerson = (Person) originalPerson.clone();
        clonedPerson.getAddress().setCountry("New Zealand");    
        Assertions.assertEquals("New Zealand", originalPerson.getAddress().getCountry());
    }
    
    @Test
    void testShallowCopy() {
        
        // shallow copy, change the existing value
        Person shalowCopyPerson = personCreator.createShallowCopy(originalPerson);
        shalowCopyPerson.getAddress().setCountry("Australia");
        Assertions.assertEquals("Australia", originalPerson.getAddress().getCountry());
    }
    
    @Test
    void testDeepCopy() {
        
        //deep copy, do not change the existing value
        Person deepCopyPerson = (Person) personCreator.createDeepCopy(originalPerson);
        deepCopyPerson.getAddress().setCountry("Japan");    
        Assertions.assertEquals("Australia", originalPerson.getAddress().getCountry());
        
    }
}
