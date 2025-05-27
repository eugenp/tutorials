package com.baeldung.spring.modulith.events.externalization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ArticleRepository extends CrudRepository<Article, Long> {
}
