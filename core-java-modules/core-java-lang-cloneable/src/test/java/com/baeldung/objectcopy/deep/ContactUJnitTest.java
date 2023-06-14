package com.baeldung.objectcopy.deep;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ContactUnitTest {

    @Test
    void whenDeepCopyContact_thenAddressesAreEqual() throws CloneNotSupportedException {
        Address address = new Address("100 Baker Street", "W1U");
        Contact originalContact = new Contact(address);

        Contact clonedContact = originalContact.clone();
        originalContact.getAddress()
            .setPostCode("W1U 8ED");

        assertNotEquals(originalContact.getAddress()
            .getPostCode(), clonedContact.getAddress()
            .getPostCode());
    }

}