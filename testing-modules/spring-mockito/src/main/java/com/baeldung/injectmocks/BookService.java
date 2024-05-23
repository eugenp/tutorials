package com.baeldung.injectmocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private DatabaseService databaseService;
    private ObjectMapper objectMapper;

    BookService(DatabaseService databaseService, ObjectMapper objectMapper) {
        this.databaseService = databaseService;
        this.objectMapper = objectMapper;
    }

    String getBook(String id) throws JsonProcessingException {
        Book book = databaseService.findById(id);
        return objectMapper.writeValueAsString(book);
    }

}
