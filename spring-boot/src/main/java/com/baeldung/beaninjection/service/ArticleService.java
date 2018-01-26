package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.model.Article;
import com.baeldung.beaninjection.model.Author;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggallo on 23/01/2018.
 */
@Service
public class ArticleService {

    public List<Article> listArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        articles.add(new Article("First Article for Baeldung", new Author("Gorka", 34)));

        return articles;
    }

}
