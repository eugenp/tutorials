package com.baeldung.apachecamel;

import org.apache.camel.main.Main;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookInfoApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookInfoApp.class);

    public static void main(String[] args) throws Exception {
        String userPrompt = "Can you provide the author and summary of the following book: The Time Regulation Institute";
        BookInfo book = runChat(userPrompt);
        LOGGER.info("Book: {}", book.name);
        LOGGER.info("Author: {}", book.author);
        LOGGER.info("Summary: {}", book.summary);
    }

    public static BookInfo runChat(String userPrompt) throws Exception {
        Main main = new Main();
        main.configure()
            .addRoutesBuilder(new BookInfoRoute());
        main.start();
        try {
            ProducerTemplate template = main.getCamelContext()
                .createProducerTemplate();
            BookInfo bookInfo = template.requestBody("direct:startBookChat", userPrompt, BookInfo.class);
            return bookInfo;
        } finally {
            main.stop();
        }
    }
}
