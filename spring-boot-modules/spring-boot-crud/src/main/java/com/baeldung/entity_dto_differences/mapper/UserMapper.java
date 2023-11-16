package com.baeldung.entity_dto_differences.mapper;

import com.baeldung.entity_dto_differences.dto.BookDto;
import com.baeldung.entity_dto_differences.dto.UserCreationDto;
import com.baeldung.entity_dto_differences.dto.UserResponseDto;
import com.baeldung.entity_dto_differences.entity.Book;
import com.baeldung.entity_dto_differences.entity.User;

import java.util.stream.Collectors;


public class UserMapper {

    public static UserResponseDto toDto(User entity) {

        return new UserResponseDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBooks().stream().map(UserMapper::toDto).collect(Collectors.toList())
        );
    }

    public static User toEntity(UserCreationDto dto) {

        return new User(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAddress(),
                dto.getBooks().stream().map(UserMapper::toEntity).collect(Collectors.toList())
        );
    }

    public static BookDto toDto(Book entity) {

        return new BookDto(entity.getName(), entity.getAuthor());
    }

    public static Book toEntity(BookDto dto) {

        return new Book(dto.getName(), dto.getAuthor());
    }
}
