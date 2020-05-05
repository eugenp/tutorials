package com.baeldung.patterns.escqrs.projectors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.baeldung.patterns.cqrs.repository.UserReadRepository;
import com.baeldung.patterns.domain.Address;
import com.baeldung.patterns.domain.Contact;
import com.baeldung.patterns.domain.UserAddress;
import com.baeldung.patterns.domain.UserContact;
import com.baeldung.patterns.es.events.Event;
import com.baeldung.patterns.es.events.UserAddressAddedEvent;
import com.baeldung.patterns.es.events.UserAddressRemovedEvent;
import com.baeldung.patterns.es.events.UserContactAddedEvent;
import com.baeldung.patterns.es.events.UserContactRemovedEvent;
import com.baeldung.patterns.es.events.UserCreatedEvent;
import com.baeldung.patterns.es.events.UserRemovedEvent;

public class UserProjector {

    UserReadRepository readRepository = new UserReadRepository();

    public UserProjector(UserReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public void project(String userId, List<Event> events) {

        for (Event event : events) {
            if (event instanceof UserCreatedEvent)
                apply(userId, (UserCreatedEvent) event);
            if (event instanceof UserRemovedEvent)
                apply(userId, (UserRemovedEvent) event);
            if (event instanceof UserAddressAddedEvent)
                apply(userId, (UserAddressAddedEvent) event);
            if (event instanceof UserAddressRemovedEvent)
                apply(userId, (UserAddressRemovedEvent) event);
            if (event instanceof UserContactAddedEvent)
                apply(userId, (UserContactAddedEvent) event);
            if (event instanceof UserContactRemovedEvent)
                apply(userId, (UserContactRemovedEvent) event);
        }

    }

    public void apply(String userId, UserCreatedEvent event) {

    }

    public void apply(String userId, UserRemovedEvent event) {

    }

    public void apply(String userId, UserAddressAddedEvent event) {
        Address address = new Address(event.getCity(), event.getState(), event.getPostCode());
        UserAddress userAddress = readRepository.getUserAddress(userId);
        if (userAddress == null)
            userAddress = new UserAddress();
        userAddress.getAddresses()
            .add(address);
        Set<Address> addresses = userAddress.getAddressByRegion()
            .get(address.getState());
        if (addresses == null)
            addresses = new HashSet<>();
        addresses.add(address);
        userAddress.getAddressByRegion()
            .put(address.getState(), addresses);
        readRepository.addUserAddress(userId, userAddress);
    }

    public void apply(String userId, UserAddressRemovedEvent event) {
        Address address = new Address(event.getCity(), event.getState(), event.getPostCode());
        UserAddress userAddress = readRepository.getUserAddress(userId);
        if (userAddress != null) {
            userAddress.getAddresses()
                .remove(address);
            Set<Address> addresses = userAddress.getAddressByRegion()
                .get(address.getState());
            if (addresses != null) {
                addresses.remove(address);
                userAddress.getAddressByRegion()
                    .put(address.getState(), addresses);
            }
            readRepository.addUserAddress(userId, userAddress);
        }
    }

    public void apply(String userId, UserContactAddedEvent event) {
        Contact contact = new Contact(event.getContactType(), event.getContactDetails());
        UserContact userContact = readRepository.getUserContact(userId);
        if (userContact == null)
            userContact = new UserContact();
        userContact.getContacts()
            .add(contact);
        Set<Contact> contacts = userContact.getContactByType()
            .get(contact.getType());
        if (contacts == null)
            contacts = new HashSet<>();
        contacts.add(contact);
        userContact.getContactByType()
            .put(contact.getType(), contacts);
        readRepository.addUserContact(userId, userContact);
    }

    public void apply(String userId, UserContactRemovedEvent event) {
        Contact contact = new Contact(event.getContactType(), event.getContactDetails());
        UserContact userContact = readRepository.getUserContact(userId);
        if (userContact != null) {
            userContact.getContacts()
                .remove(contact);
            Set<Contact> contacts = userContact.getContactByType()
                .get(contact.getType());
            if (contacts != null) {
                contacts.remove(contact);
                userContact.getContactByType()
                    .put(contact.getType(), contacts);
            }
            readRepository.addUserContact(userId, userContact);
        }
    }

}
