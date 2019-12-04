package com.baeldung.hexagon.articles.adapters.notifications;

import com.baeldung.hexagon.articles.domain.model.Article;
import com.baeldung.hexagon.articles.domain.ports.ArticleAuthorNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class ArticleAuthorSmsNotifier implements ArticleAuthorNotifier {

    private final Logger log = LoggerFactory.getLogger(ArticleAuthorSmsNotifier.class);

    @Override
    public void notifyAboutArticleCreation(final Article article) {
        /**
         * sms system integration implementation using {@link ArticleSmsModel}comes here
         */
        log.info("SMS sent to author: \"{}\"", article.author().name());
    }
}
