package com.baeldung.pattern.hexagonal.eval.domain.model;

import java.util.HashMap;
import java.util.Map;

public class AddressBook {

    private final int addressBookId;
    private Map<String, Contact> hmNicknameToContact;

    public AddressBook(int addressBookId) {
        this.addressBookId = addressBookId;
        this.hmNicknameToContact = new HashMap<>();
    }

    public int getAddressBookId() {
        return addressBookId;
    }

    public Map<String, Contact> getContacts() {
        return hmNicknameToContact;
    }

    public void setContacts(Map<String, Contact> contacts) {
        this.hmNicknameToContact = contacts;
    }
}
