package com.baeldung.resttemplate.json.service;

import com.baeldung.resttemplate.json.model.Address;
import com.baeldung.resttemplate.json.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getUsers() {
        return Arrays.asList(
                new User(1, "user1",
                        Arrays.asList(
                                new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode"),
                                new Address("address2_addressLine1", "address2_addressLine2", "address2_town", "address2_postCode"))),
                new User(2,
                        "user2",
                        Arrays.asList(
                                new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode"))),
                new User(3, "user3",
                        Arrays.asList(new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode"))));
    }
}
