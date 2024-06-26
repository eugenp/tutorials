package com.baeldung.h2.tablenotfound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.h2.tablenotfound.repository.PersonRepository;

@SpringBootTest(classes = TableNotFoundExceptionApplication.class)
class TableNotFoundExceptionIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void givenValidInitData_whenCallingFindAll_thenReturnData() {
        assertEquals(3, personRepository.findAll().size());
    }
}
