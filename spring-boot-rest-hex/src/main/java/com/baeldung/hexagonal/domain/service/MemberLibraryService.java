package com.baeldung.hexagonal.domain.service;

import java.util.UUID;

import com.baeldung.hexagonal.domain.Library;
import com.baeldung.hexagonal.domain.repository.LibraryRepository;


import com.baeldung.hexagonal.domain.Book;

public class MemberLibraryService implements LibraryService {

	private LibraryRepository libraryRepository;
	
	private final Library library;
	
	public MemberLibraryService(LibraryRepository libraryRespository) {
		this.libraryRepository = libraryRepository;
		library = new Library();
		// Initilize Library to get all book information
	}
	
	@Override
	public UUID issueBook(String title,
			UUID memberId,
			String memberName) {
		UUID bookId = 
				library.issueBook(title, memberId, memberName);
		libraryRepository.save(library.getBookEntry(title));
		return bookId;
	}
	
	@Override
	public void returnBook(String title) {
		library.returnBook(title);
		libraryRepository.save(library.getBookEntry(title));
	}
	/*
	private Book getBookEntry(String title) {
		return libraryRepository
				.findById(title)
				.orElseThrow(
						() -> new RuntimeException(
								"Book with given id does not exist!"));
	}*/

}
