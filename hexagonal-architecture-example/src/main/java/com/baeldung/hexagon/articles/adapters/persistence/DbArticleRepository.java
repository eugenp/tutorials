package com.baeldung.hexagon.articles.adapters.persistence;

import com.baeldung.hexagon.articles.domain.model.Article;
import com.baeldung.hexagon.articles.domain.model.Author;
import com.baeldung.hexagon.articles.domain.model.Content;
import com.baeldung.hexagon.articles.domain.model.Title;
import com.baeldung.hexagon.articles.domain.ports.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class DbArticleRepository implements ArticleRepository {

    private final Logger log = LoggerFactory.getLogger(DbArticleRepository.class);


    @Override
    public Article save(final Author author, final Title title, final Content content) {
        /**
         * Database integration implementation using {@link ArticleDatabaseModel} comes here
         */
        final String articleId = UUID.randomUUID().toString();
        log.info("Article: \"{}\" persisted", title.value());
        return Article.article()
                .withId(articleId)
                .withAuthor(author)
                .withTitle(title)
                .withContent(content)
                .build();
    }

    @Override
    public Article get(final String id) {
        /**
         * Database integration implementation using {@link ArticleDatabaseModel} comes here
         */
        final Title title = Title.of("Hexagonal Architecture");
        log.info("Article \"{}\" fetched", title.value());
        return Article.article()
                .withId(id)
                .withAuthor(Author
                        .author()
                        .withId(UUID.randomUUID().toString())
                        .withName("Baeldung")
                        .build())
                .withTitle(title)
                .withContent(Content.of("Lorem ipsum"))
                .build();
    }
}
