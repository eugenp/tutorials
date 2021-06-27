package com.baeldung.hexagon.architecture.domain.entity.repository;

import java.util.List;

import com.baeldung.hexagon.architecture.domain.entity.Book;

public interface BookRepositoryCustom {
	List<Book> findByName(String name);
}
