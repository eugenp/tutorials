package com.baeldung.patterns.cqrs.commands;

import java.util.HashSet;
import java.util.Set;

import com.baeldung.patterns.domain.Address;
import com.baeldung.patterns.domain.Contact;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserCommand {

    private String userId;
    private Set<Address> addresses = new HashSet<>();
    private Set<Contact> contacts = new HashSet<>();

}
