package com.baeldung.hexagonal.application.cli;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.domain.service.LibraryService;

@Component
public class CliLibraryController {

    private static final Logger LOG = 
    		LoggerFactory.getLogger(CliLibraryController.class);

    private final LibraryService libraryService;

    @Autowired
    public CliLibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public void bookIssue() {
    	LOG.info("<<Issue the book>>");
    	libraryService.issueBook("The Wealth of Nations",
    			UUID.randomUUID(),
    			"Ironman");
    }
    
    public void bookReturn() {
    	LOG.info("<<Return the book>>");
    	libraryService.returnBook("The Wealth of Nations");
    }
}