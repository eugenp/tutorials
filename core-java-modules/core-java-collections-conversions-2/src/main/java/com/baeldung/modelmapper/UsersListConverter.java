package com.baeldung.modelmapper;

import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UsersListConverter class map the property data from the list of users into the list of user names.
 *
 * @author Sasa Milenkovic
 */
public class UsersListConverter extends AbstractConverter<List<User>, List<String>> {

    @Override
    protected List<String> convert(List<User> users) {

        return users
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
