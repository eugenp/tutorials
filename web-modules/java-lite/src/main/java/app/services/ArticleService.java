package app.services;

import java.util.List;

import app.models.Article;

public interface ArticleService {

    List<Article> getArticles();

    Article search(String keyword);

}
