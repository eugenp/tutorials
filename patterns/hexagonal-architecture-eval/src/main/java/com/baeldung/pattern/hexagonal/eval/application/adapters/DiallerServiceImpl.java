package main.java.com.baeldung.pattern.hexagonal.eval.application.adapters;

import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.Contact;
import main.java.com.baeldung.pattern.hexagonal.eval.domain.ports.DiallerService;

public class DiallerServiceImpl implements DiallerService {

    private AddressBookRepositoryImpl addressBookImpl;

    public DiallerServiceImpl(AddressBookRepositoryImpl addressBookImpl) {
        this.addressBookImpl = addressBookImpl;
    }

    @Override
    public boolean dial(String nickame) {

        Contact contactToDial = addressBookImpl.retrieveContact(nickame);

        System.out.println("Dialling " + contactToDial.getNumber());
        return true;
    }
}
