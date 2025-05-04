package com.baeldung.db2database.repository;

import com.baeldung.db2database.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}