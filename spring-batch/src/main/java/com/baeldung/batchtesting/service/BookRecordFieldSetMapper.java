package com.baeldung.batchtesting.service;

import com.baeldung.batchtesting.model.BookRecord;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;


public class BookRecordFieldSetMapper implements FieldSetMapper<BookRecord> {

    @Override
    public BookRecord mapFieldSet(FieldSet fieldSet) {
        BookRecord bookRecord = new BookRecord();
        bookRecord.setBookName(fieldSet.readString("bookname"));
        bookRecord.setBookAuthor(fieldSet.readString("bookauthor"));
        bookRecord.setBookFormat(fieldSet.readString("bookformat"));
        bookRecord.setBookISBN(fieldSet.readString("isbn"));
        bookRecord.setPublishingYear(fieldSet.readString("publishyear"));
        return bookRecord;
    }

}
