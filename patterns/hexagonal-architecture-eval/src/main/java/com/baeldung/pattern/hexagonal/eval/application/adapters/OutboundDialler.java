package main.java.com.baeldung.pattern.hexagonal.eval.application.adapters;

import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.Contact;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.ports.AddressBookRepository;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.ports.ContactService;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.ports.DiallerService;

/**
 * To demonstrate, let's assume we've already got an address book. We can add a Contact,
 * save them to our address book, and then call them.
 */
public class OutboundDialler {

    public static void main(String[] args) {

        ContactServiceImpl contactServiceImpl = new ContactServiceImpl();
        AddressBookRepositoryImpl addressBookImpl = new AddressBookRepositoryImpl();
        DiallerServiceImpl diallerServiceImpl = new DiallerServiceImpl(addressBookImpl);

        Contact contact = contactServiceImpl.create("Mrs", "Samantha", "Smith", "+1", "0123456789");

        addressBookImpl.addContact("Sam", contact);

        diallerServiceImpl.dial("Sam");
    }

    AddressBookRepository addressBookRepository;
    ContactService contactService;
    DiallerService diallerService;

    OutboundDialler(AddressBookRepository addressBookRepository, ContactService contactService, DiallerService diallerService) {
        this.addressBookRepository = addressBookRepository;
        this.contactService = contactService;
        this.diallerService = diallerService;
    }
}
