package com.baeldung.hexagonal4j;

import java.util.List;

import com.baeldung.hexagonal4j.domain.article.model.Article;
import com.baeldung.hexagonal4j.infrastructure.adapter.article.cli.ArticleCli;
import com.baeldung.hexagonal4j.infrastructure.adapter.article.persistence.ArticleImMemoryDataAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String[] args) {
        ArticleCli articleCli = new ArticleCli(new ArticleImMemoryDataAdapter());
        Article article = articleCli.create(5L, "Hexagonal in 5 Minutes",
            "Hexagonal architecture is initially suggested...");
        log.info("Article is created {}",article);

        Article articleDetails = articleCli.retrieve(1L);
        log.info("Article details {}",articleDetails);

        List<Article> result = articleCli.query(5L);
        log.info("Found articles {}",result);
    }

}
