package com.baeldung.transactional.rollback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ArticleRepo extends JpaRepository<Article, Long> {
}
