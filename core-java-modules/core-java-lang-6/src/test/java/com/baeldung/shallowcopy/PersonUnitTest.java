package com.baeldung.shallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonUnitTest {
    @Test
    void whenDeepCopying_thenObjectReferencesPointToSameMemory(){

        Address address = new Address("Kings Circle" , "Mumbai" , "Maharashtra");
        Person person1 = new Person("Adam" , address);
        Person person2 = null;
        try {
            person2 = (Person) person1.clone(); //Creating clone of person1 and assigning to person2
        }
        catch(CloneNotSupportedException ce ){
            System.out.println("Error while cloning" + ce );
        }

        assertEquals(person1.getAddress().getStreet(),"Kings Circle" );
        person2.getAddress().setStreet("Marine Drive"); // changing the street value via person2
        assertNotEquals(person1.getAddress().getStreet(), "Kings Circle"); // Street name will change in shallow-copy




    }


}