package com.baeldung.hexagon.service;

import com.baeldung.hexagon.domain.Article;

import java.util.List;

public interface IArticleService {
    List<Article> articles();

    Article article(Integer id);

    boolean isPopular(Integer id);
}
