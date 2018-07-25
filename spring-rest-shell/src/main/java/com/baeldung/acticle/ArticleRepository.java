package com.baeldung.acticle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(
        path = "articles",
        collectionResourceRel = "articles",
        itemResourceRel = "article"
)
public interface ArticleRepository extends CrudRepository<Article, Long> {

    Optional<Article> findByTitle(@Param("title") String title);

}
