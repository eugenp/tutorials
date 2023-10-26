package com.baeldung.entity_dto_differences.mapper;

import com.baeldung.entity_dto_differences.dto.UserDto;
import com.baeldung.entity_dto_differences.entity.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDto(User entity) {

        return new UserDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBooks().stream().map(UserMapper::toDto).collect(Collectors.toList())
        );
    }

    public static UserDto.BookDto toDto(User.Book entity) {

        return new UserDto.BookDto(entity.getName(), entity.getAuthor());
    }
}
