package com.baeldung.modelmapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserPropertyMap class instantiates the converter to map the data from the UserList to the UsersLisDTO.
 * In the configuration method, we call a converter to do the mapping.
 *
 * @author Sasa Milenkovic
 */
public class UserPropertyMap extends PropertyMap<UserList, UserListDTO> {


    Converter<List<User>, List<String>> converter = new AbstractConverter<List<User>, List<String>>() {


        @Override
        protected List<String> convert(List<User> users) {

            return users
                    .stream()
                    .map(User::getUsername)
                    .collect(Collectors.toList());
        }
    };

    @Override
    protected void configure() {

        using(converter).map(source.getUsers(), destination.getUsernames());
    }
}
