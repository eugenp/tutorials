package com.baeldung.resttemplate.json.provider.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.baeldung.resttemplate.json.model.Address;
import com.baeldung.resttemplate.json.model.User;
import com.baeldung.resttemplate.json.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController tested;

    @Test
    void whenFetchAllUsers_thenOK() {
        // Given
        List<User> users = List.of(
                new User(1, "user1", new ArrayList<Address>(
                        Arrays.asList(
                                new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode"),
                                new Address("address2_addressLine1", "address2_addressLine2", "address2_town", "address2_postCode")))),
                new User(2,
                        "user2", new ArrayList<Address>(
                        Arrays.asList(
                                new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode")))));

        // When
        when(service.getUsers()).thenReturn(users);

        List<User> actual = tested.getUsers();

        // Then
        assertEquals(actual, users);
    }
}