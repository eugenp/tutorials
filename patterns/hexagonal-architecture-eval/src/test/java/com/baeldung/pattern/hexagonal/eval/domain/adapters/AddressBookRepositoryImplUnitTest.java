package com.baeldung.pattern.hexagonal.eval.domain.adapters;

import org.junit.jupiter.api.Test;

import com.baeldung.pattern.hexagonal.eval.application.adapters.AddressBookRepositoryImpl;
import com.baeldung.pattern.hexagonal.eval.domain.model.Contact;

public class AddressBookRepositoryImplUnitTest {

    private static final String NICKNAME = "Sam";
    private static final String FULL_NAME = "Samantha Smith";
    private static final String PHONE_NUMBER = "0123456789";

    @Test
    void whenAddingAndRetrievingSameContact_thenContactAddedIsRetrieved() {
        AddressBookRepositoryImpl addressBookRepositoryImpl = new AddressBookRepositoryImpl();

        Contact addedContact = new Contact(FULL_NAME, PHONE_NUMBER);
        addressBookRepositoryImpl.addContact(NICKNAME, addedContact);

        Contact retrievedContact = addressBookRepositoryImpl.retrieveContact(NICKNAME);

        String addedName = addedContact.getName();
        String retrievedName = retrievedContact.getName();
        assert (retrievedName.equals(addedName));

        String addedNumber = addedContact.getNumber();
        String retrievedNumber = retrievedContact.getNumber();
        assert (retrievedNumber.equals(addedNumber));
    }
}
