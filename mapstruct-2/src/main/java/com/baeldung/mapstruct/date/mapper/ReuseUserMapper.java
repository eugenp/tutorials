package com.baeldung.mapstruct.date.mapper;

import com.baeldung.mapstruct.date.model.User;
import com.baeldung.mapstruct.date.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface ReuseUserMapper {

    User toUser(UserDTO userDTO);
}