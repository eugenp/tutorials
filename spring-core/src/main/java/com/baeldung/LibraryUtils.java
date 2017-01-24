package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;

public class LibraryUtils {
    @Autowired
    private EbookRepositiry eBookRepository;
 
    public String findBook(int id) {
        return eBookRepository.titleById(id);
    }
}
