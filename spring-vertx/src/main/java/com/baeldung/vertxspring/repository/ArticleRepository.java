package com.baeldung.vertxspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.vertxspring.entity.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

}
