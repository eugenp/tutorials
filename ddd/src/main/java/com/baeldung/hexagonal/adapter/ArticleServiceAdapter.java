package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Article;
import com.baeldung.hexagonal.port.ArticleRepositoryPort;
import com.baeldung.hexagonal.port.ArticleServicePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ArticleServiceAdapter implements ArticleServicePort {

    private final ArticleRepositoryPort articleRepositoryPort;

    @Autowired
    public ArticleServiceAdapter(ArticleRepositoryPort articleRepositoryPort) {
        this.articleRepositoryPort = articleRepositoryPort;
    }

    public void createArticle(String name, String text) {
        if (articleRepositoryPort.findByName(name).isPresent() ) {
            // Throw exception that article already exists
        }

        Article article = new Article(name, text);
        articleRepositoryPort.save(article);
    }

    public Article findArticleByName(String name) {
        if (!name.isEmpty()) {
            articleRepositoryPort.findByName(name);
        }
        return null;
    }

    public List<Article> getAllArticles() {
        return articleRepositoryPort.findAll();
    }
}
