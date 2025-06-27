package com.baeldung.spring.modulith.externalization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ArticleRepository extends CrudRepository<Article, Long> {
}
