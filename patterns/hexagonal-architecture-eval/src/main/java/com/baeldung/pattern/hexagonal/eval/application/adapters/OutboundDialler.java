package com.baeldung.pattern.hexagonal.eval.application.adapters;

import com.baeldung.pattern.hexagonal.eval.domain.model.Contact;

/**
 * To demonstrate, let's assume we've already got an address book. We can add a Contact,
 * save them to our address book, and then call them.
 */
public class OutboundDialler {
    
    public static void main(String[] args) {

        AddressBookRepositoryImpl addressBookImpl = new AddressBookRepositoryImpl();
        DiallerServiceImpl diallerServiceImpl = new DiallerServiceImpl(addressBookImpl);

        Contact contact = new Contact("Samantha Smith", "0123456789");
        addressBookImpl.addContact("Sam", contact);

        diallerServiceImpl.dial("Sam");
    }
}
