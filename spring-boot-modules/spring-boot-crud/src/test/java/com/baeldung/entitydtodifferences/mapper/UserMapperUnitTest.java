package com.baeldung.entitydtodifferences.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.baeldung.entitydtodifferences.dto.BookDto;
import com.baeldung.entitydtodifferences.dto.UserCreationDto;
import com.baeldung.entitydtodifferences.dto.UserResponseDto;
import com.baeldung.entitydtodifferences.entity.Book;
import com.baeldung.entitydtodifferences.entity.User;

public class UserMapperUnitTest {

    @Test
    public void givenUserEntity_whenMappedToDto_thenMappedCorrectly() {
        //given
        Book book1 = new Book("Book1", "Author1");
        Book book2 = new Book("Book2", "Author2");
        User user = new User("John", "Doe", "123 Main St", Arrays.asList(book1, book2));
        //when
        UserResponseDto userDto = UserMapper.toDto(user);
        //then
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        List<BookDto> bookDtos = userDto.getBooks();
        assertEquals(2, bookDtos.size());
        assertEquals(book1.getName(), bookDtos.get(0)
            .getName());
        assertEquals(book1.getAuthor(), bookDtos.get(0)
            .getAuthor());
        assertEquals(book2.getName(), bookDtos.get(1)
            .getName());
        assertEquals(book2.getAuthor(), bookDtos.get(1)
            .getAuthor());
    }

    @Test
    public void givenUserDto_whenMappedToEntity_thenMappedCorrectly() {
        //given
        BookDto bookDto1 = new BookDto("Book3", "Author3");
        BookDto bookDto2 = new BookDto("Book4", "Author4");
        UserCreationDto userDto = new UserCreationDto("Jane", "Doe", "456 Oak St", Arrays.asList(bookDto1, bookDto2));
        //when
        User user = UserMapper.toEntity(userDto);
        //then
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getAddress(), userDto.getAddress());
        List<Book> books = user.getBooks();
        assertEquals(2, books.size());
        assertEquals(bookDto1.getName(), books.get(0)
            .getName());
        assertEquals(bookDto1.getAuthor(), books.get(0)
            .getAuthor());
        assertEquals(bookDto2.getName(), books.get(1)
            .getName());
        assertEquals(bookDto2.getAuthor(), books.get(1)
            .getAuthor());
    }

    @Test
    public void givenBookEntity_whenMappedToDto_thenMappedCorrectly() {
        //given
        Book book = new Book("Book5", "Author5");
        //when
        BookDto bookDto = UserMapper.toDto(book);
        //then
        assertEquals(book.getName(), bookDto.getName());
        assertEquals(book.getAuthor(), bookDto.getAuthor());
    }

    @Test
    public void givenBookDto_whenMappedToEntity_thenMappedCorrectly() {
        //given
        BookDto bookDto = new BookDto("Book6", "Author6");
        //when
        Book book = UserMapper.toEntity(bookDto);
        //then
        assertEquals(bookDto.getName(), book.getName());
        assertEquals(bookDto.getAuthor(), book.getAuthor());
    }
}
