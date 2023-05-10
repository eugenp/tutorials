package com.baeldung.deepvsshallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonDeepCopyUnitTest {

    @Test
    void whenDeepCopying_thenObjectReferencesPointToDifferentMemory(){

        AddressDeepCopy address = new AddressDeepCopy("Kings Circle" , "Mumbai" , "Maharashtra");
        PersonDeepCopy person1 = new PersonDeepCopy("Adam" , address);
        PersonDeepCopy person2 = null;
        try {
            person2 = (PersonDeepCopy) person1.clone(); //Creating clone of person1 and assigning to person2
        }
        catch(CloneNotSupportedException ce ){
            System.out.println("Error while cloning" + ce );
        }

        assertEquals(person1.getAddress().getStreet(),"Kings Circle" );
        person2.getAddress().setStreet("Marine Drive"); // changing the street value via person2
        assertEquals(person1.getAddress().getStreet(), "Kings Circle"); // Street name will remain same in deep-copy




    }


}