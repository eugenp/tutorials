package com.baeldung.patterns.hexagonal_quick;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.persistence.InMemoryBookRepositoryAdapter;
import com.baeldung.patterns.hexagonal_quick.persistence.InMemoryBorrowRepositoryAdapter;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BookData;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowRecordData;
import com.baeldung.patterns.hexagonal_quick.port.BookOutputPort;
import com.baeldung.patterns.hexagonal_quick.port.BorrowOutputPort;
import com.baeldung.patterns.hexagonal_quick.util.Converter;

@Configuration
public class HexArchQuickConfiguration {

    @Bean
    public BookOutputPort bookOutputPort(@Autowired Converter<BookData, Book> bookDataToBookConverter) {
        return new InMemoryBookRepositoryAdapter(new HashMap<>(), bookDataToBookConverter);
    }

    @Bean
    public BorrowOutputPort borrowOutputPort(@Autowired Converter<BorrowRecordData, BorrowRecord> borrowRecordConverter) {
        return new InMemoryBorrowRepositoryAdapter(new HashMap<>(), borrowRecordConverter);
    }
}
