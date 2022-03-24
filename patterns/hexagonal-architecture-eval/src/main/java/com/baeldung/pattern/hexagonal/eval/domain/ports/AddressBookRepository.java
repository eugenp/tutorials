package main.java.com.baeldung.pattern.hexagonal.eval.domain.ports;

import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.Contact;

public interface AddressBookRepository {

    public boolean addContact(String nickname, Contact contact);

    public Contact retrieveContact(String nickName);

    // Exercise: we could add update and delete methods to form a CRUD implementation.
}
