package com.baeldung.mapstruct.date.mapper;

import com.baeldung.mapstruct.date.model.User;
import com.baeldung.mapstruct.date.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface UserConversionMapper {

    User toUser(UserDto userDto);
}