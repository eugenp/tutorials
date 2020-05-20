package com.baeldung.architecture.hexagonal.sprigboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.architecture.hexagonal.business.BookStore;
import com.baeldung.architecture.hexagonal.business.bordery.driven.BookPersistencePort;
import com.baeldung.architecture.hexagonal.business.bordery.driver.BookRentalPort;

@Configuration
class BookStoreStarter {

    @Bean
    BookRentalPort inject(BookPersistencePort bookPersistence) {
        return new BookStore(bookPersistence);
    }
}
