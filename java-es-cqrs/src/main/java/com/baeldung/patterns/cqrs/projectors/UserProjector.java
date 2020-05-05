package com.baeldung.patterns.cqrs.projectors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.baeldung.patterns.cqrs.repository.UserReadRepository;
import com.baeldung.patterns.domain.Address;
import com.baeldung.patterns.domain.Contact;
import com.baeldung.patterns.domain.User;
import com.baeldung.patterns.domain.UserAddress;
import com.baeldung.patterns.domain.UserContact;

public class UserProjector {

    UserReadRepository readRepository = new UserReadRepository();

    public UserProjector(UserReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public void project(User user) {
        UserContact userContact = readRepository.getUserContact(user.getUserid());
        if (userContact == null)
            userContact = new UserContact();
        userContact.setContacts(user.getContacts());
        Map<String, Set<Contact>> contactByType = new HashMap<>();
        for (Contact contact : user.getContacts()) {
            Set<Contact> contacts = contactByType.get(contact.getType());
            if (contacts == null)
                contacts = new HashSet<>();
            contacts.add(contact);
            contactByType.put(contact.getType(), contacts);
        }
        userContact.setContactByType(contactByType);
        readRepository.addUserContact(user.getUserid(), userContact);

        UserAddress userAddress = readRepository.getUserAddress(user.getUserid());
        if (userAddress == null)
            userAddress = new UserAddress();
        userAddress.setAddresses(user.getAddresses());
        Map<String, Set<Address>> addressByRegion = new HashMap<>();
        for (Address address : user.getAddresses()) {
            Set<Address> addresses = addressByRegion.get(address.getState());
            if (addresses == null)
                addresses = new HashSet<>();
            addresses.add(address);
            addressByRegion.put(address.getState(), addresses);
        }
        userAddress.setAddressByRegion(addressByRegion);
        readRepository.addUserAddress(user.getUserid(), userAddress);

    }

}
