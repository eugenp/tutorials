package com.baeldung.hexagon.articles.adapters.authorservice;

import com.baeldung.hexagon.articles.domain.model.Author;
import com.baeldung.hexagon.articles.domain.model.AuthorId;
import com.baeldung.hexagon.articles.domain.ports.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class ExternalServiceClientAuthorRepository implements AuthorRepository {
    private final Logger log = LoggerFactory.getLogger(ExternalServiceClientAuthorRepository.class);

    @Override
    public Author get(final AuthorId authorId) {
        /**
         * external author service integration implementation comes here
         */
        log.info("Author: \"Baeldung\" fetched", authorId.value());
        return Author
                .author()
                .withId(authorId)
                .withName("Baeldung")
                .build();
    }
}
