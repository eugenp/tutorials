package com.baeldung.hexagon.articles.adapters.notifications;

import com.baeldung.hexagon.articles.domain.Article;
import com.baeldung.hexagon.articles.domain.ports.UserNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class MailArticleNotifier implements UserNotifier {

    private final Logger log = LoggerFactory.getLogger(MailArticleNotifier.class);

    @Override
    public void notifyAbout(final Article article) {
        /**
         * mail system integration implementation using {@link ArticleMailModel} comes here
         */
        log.info("Mail sent to author {}", article.author().name());
    }
}
