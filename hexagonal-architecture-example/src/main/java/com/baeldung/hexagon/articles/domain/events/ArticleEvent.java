package com.baeldung.hexagon.articles.domain.events;

import com.baeldung.hexagon.articles.domain.Article;

public interface ArticleEvent {
    Article article();
}
