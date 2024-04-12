package com.baeldung.entitydtodifferences.mapper;

import java.util.stream.Collectors;

import com.baeldung.entitydtodifferences.dto.BookDto;
import com.baeldung.entitydtodifferences.dto.UserCreationDto;
import com.baeldung.entitydtodifferences.dto.UserResponseDto;
import com.baeldung.entitydtodifferences.entity.Book;
import com.baeldung.entitydtodifferences.entity.User;

public class UserMapper {

    public static UserResponseDto toDto(User entity) {
        return new UserResponseDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBooks()
            .stream()
            .map(UserMapper::toDto)
            .collect(Collectors.toList()));
    }

    public static User toEntity(UserCreationDto dto) {
        return new User(dto.getFirstName(), dto.getLastName(), dto.getAddress(), dto.getBooks()
            .stream()
            .map(UserMapper::toEntity)
            .collect(Collectors.toList()));
    }

    public static BookDto toDto(Book entity) {
        return new BookDto(entity.getName(), entity.getAuthor());
    }

    public static Book toEntity(BookDto dto) {
        return new Book(dto.getName(), dto.getAuthor());
    }
}
