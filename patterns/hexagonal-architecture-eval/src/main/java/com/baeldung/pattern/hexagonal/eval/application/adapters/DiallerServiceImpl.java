package main.java.com.baeldung.pattern.hexagonal.eval.application.adapters;

import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.Contact;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.ports.DiallerService;

public class DiallerServiceImpl implements DiallerService {

    public AddressBookRepositoryImpl addressBookImpl;

    public DiallerServiceImpl(AddressBookRepositoryImpl addressBookImpl) {
        this.addressBookImpl = addressBookImpl;
    }

    @Override
    public boolean dial(String name) {

        Contact contactToDial = addressBookImpl.retrieveContact(name);

        System.out.println("Dialling " + contactToDial.getContactNumber()
            .getNumber());
        return true;
    }
}
