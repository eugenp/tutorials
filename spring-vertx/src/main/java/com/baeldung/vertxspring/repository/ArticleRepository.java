package com.baeldung.vertxspring.repository;

import com.baeldung.vertxspring.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
