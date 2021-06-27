package com.baeldung.hexagon.architecture.domain.entity.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagon.architecture.domain.entity.Book;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

	MongoTemplate template;

	@Override
	public List<Book> findByName(String name) {
		Query query = new Query(Criteria.where("title").is(name));
		return template.find(query, Book.class);

	}

}
