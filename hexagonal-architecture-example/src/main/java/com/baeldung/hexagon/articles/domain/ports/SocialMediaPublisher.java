package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.Article;

public interface SocialMediaPublisher {

    void publish(Article article);

}
