package com.baeldung.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.baeldung.batch.model.Book;
import com.baeldung.batch.model.BookRecord;

public class BookItemProcessor implements ItemProcessor<BookRecord, Book> {

    private static Logger LOGGER = LoggerFactory.getLogger(BookItemProcessor.class);

    @Override
    public Book process(BookRecord item) throws Exception {
        Book book = new Book();
        book.setAuthor(item.getBookAuthor());
        book.setName(item.getBookName());
        LOGGER.info("Processing book {}", book);
        return book;
    }

}
