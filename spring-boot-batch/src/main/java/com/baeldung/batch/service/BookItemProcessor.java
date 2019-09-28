package com.baeldung.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.baeldung.batch.model.Book;

public class BookItemProcessor implements ItemProcessor<Book, Book> {

    private static Logger LOGGER = LoggerFactory.getLogger(BookItemProcessor.class);

    @Override
    public Book process(Book item) throws Exception {
        // no actual processing
        LOGGER.info("Processing book {}", item);
        return item;
    }

}
