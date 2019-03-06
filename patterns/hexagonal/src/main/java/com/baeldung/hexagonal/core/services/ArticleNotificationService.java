package com.baeldung.hexagonal.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baeldung.hexagonal.core.domain.Article;
import com.baeldung.hexagonal.framework_layer.ports.ArticleNotificationPort;

@Service
public class ArticleNotificationService {

    @Autowired
    ArticleNotificationPort articleNotification;

    public void publish(Article article) {

        articleNotification.notificate(article);

    }

}
