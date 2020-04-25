package com.baeldung.hexagonal;

public class BookService {

	private BookDaoInterface dao;

	public BookService(BookDaoInterface bookDao) {
		dao = bookDao;
	}

	public Book search(String isbn) {
		return dao.get(isbn);
	}
}
