package com.baeldung.mapper;

import org.mapstruct.Named;

import com.baeldung.entity.Book;

public class MappingHelper {

    @Named("mapAuthor")
    public static String mapAuthor(Book book) {
        if (book.getAuthor() == null) {
            return null;
        }
        return book.getAuthor()
            .getFirstName() + " " + book.getAuthor()
            .getLastName();
    }
}