package com.baeldung.hexagonal.domain.service;

import java.util.UUID;

public interface LibraryService {
    
    public UUID issueBook(String title,
        UUID memberId,
        String memberName);
    
    public void returnBook(String title);
}
