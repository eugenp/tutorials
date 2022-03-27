package com.baeldung.pattern.hexagonal.eval.application.adapters;

import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.pattern.hexagonal.eval.domain.model.Contact;
import com.baeldung.pattern.hexagonal.eval.domain.ports.DiallerService;

public class DiallerServiceImpl implements DiallerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiallerServiceImpl.class);

    private AddressBookRepositoryImpl addressBookImpl;

    public DiallerServiceImpl(AddressBookRepositoryImpl addressBookImpl) {

        this.addressBookImpl = addressBookImpl;
    }

    @Override
    public boolean dial(String nickname) {

        Contact contactToDial = addressBookImpl.retrieveContact(nickname);

        // we only log the call details rather than actually dialling the contact
        LOGGER.info(getDialInfoStr(nickname, contactToDial));

        return true;
    }

    private String getDialInfoStr(String nickname, Contact contactToDial) {
        return new StringJoiner(" ").add("Dialling")
            .add(contactToDial.getName())
            .add("aka")
            .add(nickname)
            .add("on")
            .add(contactToDial.getNumber())
            .toString();
    }
}
