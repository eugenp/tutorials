package com.baeldung.hexagonal.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import com.baeldung.hexagonal.domain.service.LibraryService;
import com.baeldung.hexagonal.application.request.IssueBookRequest;

@RestController
@RequestMapping
public class LibraryController {
	
	private final LibraryService libraryService;
	
	@Autowired
	public LibraryController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}
	
	@PostMapping(value = "/{title}/issue", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	UUID issueBook(@PathVariable final String title,
			@RequestBody final IssueBookRequest issueBookRequest) {
		return libraryService.issueBook(title, 
				issueBookRequest.getMemberId(), 
				issueBookRequest.getMemberName());		
	}
	
	@PostMapping(value = "/{title}/return", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	void returnBook(@PathVariable final String title) {
		libraryService.returnBook(title);		
	}	
}
