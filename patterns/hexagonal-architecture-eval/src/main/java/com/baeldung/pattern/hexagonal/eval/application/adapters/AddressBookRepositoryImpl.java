package main.java.com.baeldung.pattern.hexagonal.eval.application.adapters;

import java.util.Map;

import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.AddressBook;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.Contact;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.ports.AddressBookRepository;

public class AddressBookRepositoryImpl implements AddressBookRepository {

    // We could replace this with a database rather than storing the address book here.
    private AddressBook addressBook = new AddressBook(1);

    @Override
    public boolean addContact(String nickname, Contact contact) {

        Map<String, Contact> contacts = addressBook.getContacts();

        contacts.put(nickname, contact);

        return true;
    }

    @Override
    public Contact retrieveContact(String nickname) {
        Map<String, Contact> contacts = addressBook.getContacts();

        return contacts.get(nickname);
    }
}
