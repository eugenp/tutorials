package io.github.venkat1701.stringtodatemapstruct.mapper;

import io.github.venkat1701.stringtodatemapstruct.dto.UserDTO;
import io.github.venkat1701.stringtodatemapstruct.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    UserDTO userToUserDto(User user);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    User userDtoToUser(UserDTO userDTO);
}
