package com.baeldung.hexagonal.infrastructure.adapter.jpa.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.domain.model.Book;
import com.baeldung.hexagonal.domain.port.BookPersistencePort;
import com.baeldung.hexagonal.infrastructure.adapter.jpa.entity.BookEntity;
import com.baeldung.hexagonal.infrastructure.adapter.jpa.repository.BookJpaRepository;

@Component
public class BookJpaAdapter implements BookPersistencePort {

	@Autowired
	private BookJpaRepository bookJpaRepository;

	public void addBook(Book book) {

		BookEntity bookEntity = new BookEntity();
		BeanUtils.copyProperties(book, bookEntity);
		bookJpaRepository.save(bookEntity);

	}

	@Override
	public void removeBook(Book book) {

		BookEntity bookEntity = new BookEntity();
		BeanUtils.copyProperties(book, bookEntity);
		bookJpaRepository.delete(bookEntity);

	}

	@Override
	public List<Book> getBooks() {

		List<BookEntity> bookEntityList = bookJpaRepository.findAll();
		List<Book> bookList = new ArrayList<Book>();
		bookEntityList.stream().forEach(entityBook -> {
			Book book = new Book();
			BeanUtils.copyProperties(entityBook, book);
			bookList.add(book);
		});
		return bookList;
	}

	@Override
	public Book getBookById(Integer bookId) throws Exception {
		BookEntity bookEntity = bookJpaRepository.findByBookId(bookId);
		Book book = new Book();
		if (null != bookEntity) {
			BeanUtils.copyProperties(bookEntity, book);
		} else {
			throw new Exception("Book not found");
		}
		return book;
	}

}
