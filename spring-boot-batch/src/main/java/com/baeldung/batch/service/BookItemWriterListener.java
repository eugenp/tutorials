package com.baeldung.batch.service;

import java.text.Collator;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import com.baeldung.batch.model.Book;

public class BookItemWriterListener implements ItemWriteListener<Book> {

    private static Logger LOGGER = LoggerFactory.getLogger(BookItemWriterListener.class);

    @Override
    public void beforeWrite(List<? extends Book> items) {
        LOGGER.info("Attemtpting to write {} books", items.size());
    }

    @Override
    public void afterWrite(List<? extends Book> items) {
        // nothing
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Book> items) {
        LOGGER.error("Encoutered exception {} for follwing number of books {} ", exception.getMessage(), items.size());
    }

}
