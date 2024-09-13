package io.github.venkat1701.stringtodatemapstruct.service;

import io.github.venkat1701.stringtodatemapstruct.dto.UserDTO;
import io.github.venkat1701.stringtodatemapstruct.mapper.UserMapper;
import io.github.venkat1701.stringtodatemapstruct.model.User;

public class UserService {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserDTO convertToUserDTO(User user) {
        return userMapper.userToUserDto(user);
    }

    public User convertToUser(UserDTO userDTO) {
        return userMapper.userDtoToUser(userDTO);
    }
}