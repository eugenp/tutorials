package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.Article;

public interface UserNotifier {

    void notifyAbout(Article article);

}
