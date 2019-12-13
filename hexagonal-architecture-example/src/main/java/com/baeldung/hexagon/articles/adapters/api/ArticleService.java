package com.baeldung.hexagon.articles.adapters.api;

import com.baeldung.hexagon.articles.domain.ArticleFacade;
import org.springframework.stereotype.Component;

@Component
class ArticleService {

    private final ArticleFacade articleFacade;

    ArticleService(final ArticleFacade articleFacade) {
        this.articleFacade = articleFacade;
    }

    ArticleResponse get(final String articleId) {
        return ArticleResponse.of(articleFacade.get(articleId));
    }

    ArticleIdResponse create(final ArticleRequest articleRequest) {
        final String articleId = articleFacade.create(articleRequest.authorId(), articleRequest.title(), articleRequest.content());
        return ArticleIdResponse.of(articleId);
    }
}
