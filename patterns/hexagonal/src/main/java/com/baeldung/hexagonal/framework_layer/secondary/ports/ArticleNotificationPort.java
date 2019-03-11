package com.baeldung.hexagonal.framework_layer.secondary.ports;

import com.baeldung.hexagonal.core.domain.Article;

public interface ArticleNotificationPort {

    void notify(Article article);

}
