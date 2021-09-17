package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model.mappers;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.infrastructure.controller.UserDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @IterableMapping(elementTargetType = User.class)
    User toUserEntity(UserDTO userDTO);

    @IterableMapping(elementTargetType = UserDTO.class)
    UserDTO toUserDTO(User userEntity);
}
