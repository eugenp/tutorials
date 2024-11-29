package com.baeldung.mapstruct.date.mapper;

import com.baeldung.mapstruct.date.model.User;
import com.baeldung.mapstruct.date.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateMapper.class)
public interface UserConversionMapper {

    @Mapping(source = "birthDate", target = "birthDate")
    User toUser(UserDto userDto);
}