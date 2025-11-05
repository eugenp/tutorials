package com.baeldung.deserialization.exception;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class EmailService {
    private final static Logger log = Logger.getLogger(EmailService.class.getName());
    private final List<String> articles = new ArrayList<>();

    public void sendNewsletter(String article) {
        log.info("Sending newsletter for article: " + article);
        articles.add(article);
    }

    public List<String> getArticles() {
        return articles;
    }
}
