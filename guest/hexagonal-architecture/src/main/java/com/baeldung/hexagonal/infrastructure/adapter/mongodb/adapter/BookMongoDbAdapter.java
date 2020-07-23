package com.baeldung.hexagonal.infrastructure.adapter.mongodb.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.domain.model.Book;
import com.baeldung.hexagonal.domain.port.BookPersistencePort;
import com.baeldung.hexagonal.infrastructure.adapter.mongodb.entity.BookMongoDbEntity;
import com.baeldung.hexagonal.infrastructure.adapter.mongodb.repository.BookMongoDbRepository;

@Component
public class BookMongoDbAdapter implements BookPersistencePort {

	@Autowired
	private BookMongoDbRepository bookRepository;

	public void addBook(Book book) {

		BookMongoDbEntity bookEntity = new BookMongoDbEntity();
		BeanUtils.copyProperties(book, bookEntity);
		bookRepository.save(bookEntity);

	}

	@Override
	public void removeBook(Book book) {

		BookMongoDbEntity bookEntity = new BookMongoDbEntity();
		BeanUtils.copyProperties(book, bookEntity);
		bookRepository.delete(bookEntity);

	}

	@Override
	public List<Book> getBooks() {

		List<BookMongoDbEntity> bookEntityList = bookRepository.findAll();
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
		BookMongoDbEntity bookEntity = bookRepository.findByBookId(bookId);
		Book book = new Book();
		if (null != bookEntity) {
			BeanUtils.copyProperties(bookEntity, book);
		} else {
			throw new Exception("Book not found");
		}
		return book;
	}

}
