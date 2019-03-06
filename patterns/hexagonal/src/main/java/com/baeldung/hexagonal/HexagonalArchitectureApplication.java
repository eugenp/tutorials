package com.baeldung.hexagonal;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.baeldung.hexagonal.core.domain.Article;
import com.baeldung.hexagonal.core.services.ArticleNotificationService;

@SpringBootApplication
public class HexagonalArchitectureApplication {

    @Autowired
    ArticleNotificationService serviceNotification;

    public static void main(String[] args) {

        SpringApplication.run(HexagonalArchitectureApplication.class, args);

    }

    @PostConstruct
    private void init() {

        StringBuilder content = new StringBuilder();
        content.append("In this quick article...");

        Article article = new Article();

        article.setTitle("Hexagonal Architecture");
        article.setSubject("Hexagonal Architecture in Java");
        article.setContent(content);

        serviceNotification.publish(article);

    }

}
