package com.baeldung.hexagonal.framework_layer.ports;

import com.baeldung.hexagonal.core.domain.Article;

public interface ArticleNotificationPort {

    void notificate(Article article);

}
