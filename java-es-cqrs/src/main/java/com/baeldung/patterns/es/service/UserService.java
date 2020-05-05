package com.baeldung.patterns.es.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.baeldung.patterns.domain.Address;
import com.baeldung.patterns.domain.Contact;
import com.baeldung.patterns.domain.User;
import com.baeldung.patterns.es.events.UserAddressAddedEvent;
import com.baeldung.patterns.es.events.UserAddressRemovedEvent;
import com.baeldung.patterns.es.events.UserContactAddedEvent;
import com.baeldung.patterns.es.events.UserContactRemovedEvent;
import com.baeldung.patterns.es.events.UserCreatedEvent;
import com.baeldung.patterns.es.repository.EventStore;

public class UserService {

    private EventStore repository;

    public UserService(EventStore repository) {
        this.repository = repository;
    }

    public void createUser(String userId, String firstName, String lastName) {
        UserCreatedEvent event = new UserCreatedEvent(userId, firstName, lastName);
        repository.addEvent(userId, event);
    }

    public void updateUser(String userId, Set<Contact> contacts, Set<Address> addresses) throws Exception {
        User user = UserUtility.recreateUserState(repository, userId);
        if (user == null)
            throw new Exception("User does not exist.");

        List<Contact> contactsToRemove = user.getContacts()
            .stream()
            .filter(c -> !contacts.contains(c))
            .collect(Collectors.toList());
        for (Contact contact : contactsToRemove) {
            UserContactRemovedEvent contactRemovedEvent = new UserContactRemovedEvent(contact.getType(), contact.getDetail());
            repository.addEvent(userId, contactRemovedEvent);
        }

        List<Contact> contactsToAdd = contacts.stream()
            .filter(c -> !user.getContacts()
                .contains(c))
            .collect(Collectors.toList());
        for (Contact contact : contactsToAdd) {
            UserContactAddedEvent contactAddedEvent = new UserContactAddedEvent(contact.getType(), contact.getDetail());
            repository.addEvent(userId, contactAddedEvent);
        }

        List<Address> addressesToRemove = user.getAddresses()
            .stream()
            .filter(a -> !addresses.contains(a))
            .collect(Collectors.toList());
        for (Address address : addressesToRemove) {
            UserAddressRemovedEvent addressRemovedEvent = new UserAddressRemovedEvent(address.getCity(), address.getState(), address.getPostcode());
            repository.addEvent(userId, addressRemovedEvent);
        }

        List<Address> addressesToAdd = addresses.stream()
            .filter(a -> !user.getAddresses()
                .contains(a))
            .collect(Collectors.toList());
        for (Address address : addressesToAdd) {
            UserAddressAddedEvent addressAddedEvent = new UserAddressAddedEvent(address.getCity(), address.getState(), address.getPostcode());
            repository.addEvent(userId, addressAddedEvent);
        }
    }

    public Set<Contact> getContactByType(String userId, String contactType) throws Exception {
        User user = UserUtility.recreateUserState(repository, userId);
        if (user == null)
            throw new Exception("User does not exist.");
        Set<Contact> contacts = user.getContacts();
        return contacts.stream()
            .filter(c -> c.getType()
                .equals(contactType))
            .collect(Collectors.toSet());
    }

    public Set<Address> getAddressByRegion(String userId, String state) throws Exception {
        User user = UserUtility.recreateUserState(repository, userId);
        if (user == null)
            throw new Exception("User does not exist.");
        Set<Address> addresses = user.getAddresses();
        return addresses.stream()
            .filter(a -> a.getState()
                .equals(state))
            .collect(Collectors.toSet());
    }

}
