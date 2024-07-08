package com.baeldung.hexagonal.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.persistence.entity.Article;

@Repository
public interface CommentRepository extends JpaRepository<Article, Long> {
}
