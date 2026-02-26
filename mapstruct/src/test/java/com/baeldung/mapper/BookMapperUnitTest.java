package com.baeldung.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.dto.BookDTO;
import com.baeldung.entity.Author;
import com.baeldung.entity.Book;

public class BookMapperUnitTest {

    @Test
    public void givenBook_whenMapsWithExpression_thenProducesCorrectDto() {
        Author author = new Author("John", "Doe");
        Book book = new Book("MapStruct Basics", author);
        BookDTO dto = BookMapper.INSTANCE.toDTOWithExpression(book);

        assertEquals("MapStruct Basics", dto.getTitle());
        assertEquals("John Doe", dto.getAuthor());
    }

    @Test
    public void givenBook_whenMapsWithAfterMapping_thenProducesCorrectDto() {
        Author author = new Author("John", "Doe");
        Book book = new Book("MapStruct Basics", author);
        BookDTO dto = BookMapper.INSTANCE.toDTOWithAfterMapping(book);

        assertEquals("MapStruct Basics", dto.getTitle());
        assertEquals("John Doe", dto.getAuthor());
    }

    @Test
    public void givenBook_whenMapsWithHelper_thenProducesCorrectDto() {
        Author author = new Author("John", "Doe");
        Book book = new Book("MapStruct Basics", author);
        BookDTO dto = BookMapper.INSTANCE.toDTOWithHelper(book);

        assertEquals("MapStruct Basics", dto.getTitle());
        assertEquals("John Doe", dto.getAuthor());
    }
}
