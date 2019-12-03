package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.Article;
import com.baeldung.hexagon.articles.domain.ArticleId;
import com.baeldung.hexagon.articles.domain.Author;
import com.baeldung.hexagon.articles.domain.Content;
import com.baeldung.hexagon.articles.domain.Title;

public interface ArticleRepository {

    Article save(Author author, Title title, Content content);

    Article get(ArticleId id);
}
