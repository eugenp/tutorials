package com.baeldung.objectcopy.shallow;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ContactUnitTest {

    @Test
    void whenShallowCopyContact_thenAddressesAreEqual() throws CloneNotSupportedException {
        Address address = new Address("100 Baker Street", "W1U");
        Contact originalContact = new Contact(address);

        Contact clonedContact = originalContact.clone();
        originalContact.getAddress()
            .setPostCode("W1U 8ED");

        assertEquals(originalContact.getAddress()
            .getPostCode(), clonedContact.getAddress()
            .getPostCode());
    }

}