package com.baeldung.hexagonal.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.hexagonal.persistence.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
