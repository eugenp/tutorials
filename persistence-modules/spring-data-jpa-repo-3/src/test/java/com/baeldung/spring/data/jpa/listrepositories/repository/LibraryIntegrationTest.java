package com.baeldung.spring.data.jpa.listrepositories.repository;

import com.baeldung.spring.data.jpa.listrepositories.entity.Library;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class LibraryIntegrationTest {

    @Autowired
    private LibraryRepository libraryRepository;

    @Test
    @Transactional
    public void givenLibrary_whenGetAddressesAndGetBooks_thenGetListOfItems(){
        Library library = new Library();
        library.setAddresses(Arrays.asList("Address 1", "Address 2"));
        library.setBooks(Arrays.asList("Book 1", "Book 2"));

        libraryRepository.save(library);
        Library lib = libraryRepository.findById(library.getId().longValue());

        Assertions.assertEquals(2, lib.getAddresses().size());
        Assertions.assertEquals(2, lib.getBooks().size());
    }

}
