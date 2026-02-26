package com.baeldung.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.baeldung.dto.BookDTO;
import com.baeldung.entity.Author;
import com.baeldung.entity.Book;

@Mapper(uses = MappingHelper.class)
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    @Mapping(target = "author", expression = "java(book.getAuthor().getFirstName() + \" \" + book.getAuthor().getLastName())")
    BookDTO toDTOWithExpression(Book book);

    @Mapping(target = "author", ignore = true)
    BookDTO toDTOWithAfterMapping(Book book);

    @AfterMapping
    default void setAuthor(@MappingTarget BookDTO bookDTO, Book book) {
        Author author = book.getAuthor();
        if (author != null) {
            bookDTO.setAuthor(author.getFirstName() + " " + author.getLastName());
        }
    }

    @Mapping(target = "author", source = "book", qualifiedByName = "mapAuthor")
    BookDTO toDTOWithHelper(Book book);
}
