package com.baeldung.deepvsshallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonShallowCopyTest {
    @Test
    void whenDeepCopying_thenObjectReferencesPointToSameMemory(){

        AddressShallowCopy address = new AddressShallowCopy("Kings Circle" , "Mumbai" , "Maharashtra");
        PersonShallowCopy person1 = new PersonShallowCopy("Adam" , address);
        PersonShallowCopy person2 = null;
        try {
            person2 = (PersonShallowCopy) person1.clone(); //Creating clone of person1 and assigning to person2
        }
        catch(CloneNotSupportedException ce ){
            System.out.println("Error while cloning" + ce );
        }

        assertEquals(person1.getAddress().getStreet(),"Kings Circle" );
        person2.getAddress().setStreet("Marine Drive"); // changing the street value via person2
        assertNotEquals(person1.getAddress().getStreet(), "Kings Circle"); // Street name will change in shallow-copy




    }


}