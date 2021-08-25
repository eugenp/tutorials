package com.baeldung.designpatterns.dtopattern.api;

import com.baeldung.designpatterns.dtopattern.domain.Role;
import com.baeldung.designpatterns.dtopattern.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class Mapper {
    public UserDTO toDto(User user) {
        String name = user.getName();
        List<String> roles = user
                .getRoles()
                .stream()
                .map(Role::getName)
                .collect(toList());

        return new UserDTO(name, roles);
    }

    public User toUser(UserCreationDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getPassword(), new ArrayList<>());
    }
}
