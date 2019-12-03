package com.baeldung.hexagon.articles.adapters.notifications;

import com.baeldung.hexagon.articles.domain.Article;
import com.baeldung.hexagon.articles.domain.ports.UserNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class SmsArticleNotifier implements UserNotifier {

    private final Logger log = LoggerFactory.getLogger(SmsArticleNotifier.class);

    @Override
    public void notifyAbout(final Article article) {
        /**
         * sms system integration implementation using {@link ArticleSmsModel}comes here
         */
        log.info("SMS sent to author {}", article.author().name());
    }
}
