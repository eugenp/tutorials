package com.baeldung.hexagon.articles.adapters.persistence;

import com.baeldung.hexagon.articles.domain.Article;
import com.baeldung.hexagon.articles.domain.ArticleId;
import com.baeldung.hexagon.articles.domain.Author;
import com.baeldung.hexagon.articles.domain.AuthorId;
import com.baeldung.hexagon.articles.domain.Content;
import com.baeldung.hexagon.articles.domain.Title;
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
        log.info("Article persisted with id {} title {} and content {}", articleId, title.value(), content.value());
        return Article.article()
                .withId(ArticleId.of(articleId))
                .withAuthor(author)
                .withTitle(title)
                .withContent(content)
                .build();
    }

    @Override
    public Article get(final ArticleId id) {
        /**
         * Database integration implementation using {@link ArticleDatabaseModel} comes here
         */
        log.info("Article with id {} fetched", id.value());
        return Article.article()
                .withId(id)
                .withAuthor(Author
                        .author()
                        .withId(AuthorId.of(UUID.randomUUID().toString()))
                        .withName("William Shakespeare")
                        .build())
                .withTitle(Title.of("Lorem ipsum"))
                .withContent(Content.of("To be or not to be"))
                .build();
    }
}
