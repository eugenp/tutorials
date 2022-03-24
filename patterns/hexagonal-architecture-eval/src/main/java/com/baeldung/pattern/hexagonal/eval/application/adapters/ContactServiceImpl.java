package main.java.com.baeldung.pattern.hexagonal.eval.application.adapters;

import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.Contact;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.ContactName;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.ContactNumber;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.ports.ContactService;

public class ContactServiceImpl implements ContactService {

    @Override
    public Contact create(String title, String firstName, String lastName, String internationalCode, String number) {
        ContactName contactName = new ContactName(title, firstName, lastName);
        ContactNumber contactNumber = new ContactNumber(internationalCode, number);
        Contact contact = new Contact(contactName, contactNumber);

        return contact;
    }
}
