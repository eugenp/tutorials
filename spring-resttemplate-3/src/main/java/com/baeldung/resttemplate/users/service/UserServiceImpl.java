package com.baeldung.resttemplate.users.service;

import com.baeldung.resttemplate.users.model.Address;
import com.baeldung.resttemplate.users.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> getUsers() {
        return List.of(
                new User(1, "user1", new ArrayList<Address>(
                        Arrays.asList(
                                new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode"),
                                new Address("address2_addressLine1", "address2_addressLine2", "address2_town", "address2_postCode")))),
                new User(2,
                        "user2", new ArrayList<Address>(
                        Arrays.asList(
                                new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode")))),
                new User(3, "user3", new ArrayList<Address>(
                        Arrays.asList(new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode")))));
    }
}
