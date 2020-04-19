package com.baeldung.util;

import com.baeldung.model.User;
import com.baeldung.model.UserDTO;
import com.baeldung.model.UserList;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sasam0320
 * @date 4/18/2020
 */

public class UserPropertyMap extends PropertyMap<UserList, UserDTO> {


    Converter<List<User>, List<String>> converter = new AbstractConverter<List<User>, List<String>>() {

        List<String> usernames = new ArrayList<>();

        protected List<String> convert(List<User> users) {

            users.forEach(user -> usernames.add(user.getUserName()));
            return usernames;
        }

    };

    @Override
    protected void configure() {
        using(converter).map(source.getUsers(), destination.getUsernames());
    }
}
