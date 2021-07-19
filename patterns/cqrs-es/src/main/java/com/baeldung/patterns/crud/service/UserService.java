package com.baeldung.patterns.crud.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.baeldung.patterns.crud.repository.UserRepository;
import com.baeldung.patterns.domain.Address;
import com.baeldung.patterns.domain.Contact;
import com.baeldung.patterns.domain.User;

public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(String userId, String firstName, String lastName) {
        User user = new User(userId, firstName, lastName);
        repository.addUser(userId, user);
    }

    public void updateUser(String userId, Set<Contact> contacts, Set<Address> addresses) throws Exception {
        User user = repository.getUser(userId);
        if (user == null)
            throw new Exception("User does not exist.");
        user.setContacts(contacts);
        user.setAddresses(addresses);
        repository.addUser(userId, user);
    }

    public Set<Contact> getContactByType(String userId, String contactType) throws Exception {
        User user = repository.getUser(userId);
        if (user == null)
            throw new Exception("User does not exit.");
        Set<Contact> contacts = user.getContacts();
        return contacts.stream()
            .filter(c -> c.getType()
                .equals(contactType))
            .collect(Collectors.toSet());
    }

    public Set<Address> getAddressByRegion(String userId, String state) throws Exception {
        User user = repository.getUser(userId);
        if (user == null)
            throw new Exception("User does not exist.");
        Set<Address> addresses = user.getAddresses();
        return addresses.stream()
            .filter(a -> a.getState()
                .equals(state))
            .collect(Collectors.toSet());
    }

}
