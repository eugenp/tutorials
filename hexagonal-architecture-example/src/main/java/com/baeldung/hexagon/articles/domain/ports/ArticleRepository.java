package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.model.Article;
import com.baeldung.hexagon.articles.domain.model.Author;
import com.baeldung.hexagon.articles.domain.model.Content;
import com.baeldung.hexagon.articles.domain.model.Title;

public interface ArticleRepository {

    Article save(Author author, Title title, Content content);

    Article get(String id);
}
