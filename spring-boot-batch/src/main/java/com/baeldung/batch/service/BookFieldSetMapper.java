package com.baeldung.batch.service;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.baeldung.batch.model.Book;

public class BookFieldSetMapper implements FieldSetMapper<Book> {

    @Override
    public Book mapFieldSet(FieldSet fieldSet) throws BindException {
        Book book = new Book();
        book.setName(fieldSet.readString("bookname"));
        book.setAuthor(fieldSet.readString("bookauthor"));
        return book;
    }

}
