package com.baeldung.spring.controller.rss;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class ArticleRssController {

    @GetMapping(value = "/rss1")
    public String articleMvcFeed() {
        return "articleFeedView";
    }

    @GetMapping(value = "/rss2")
    @ResponseBody
    public ArticleFeed articleRestFeed2() {
        ArticleFeed feed = new ArticleFeed();
        feed.setLink("http://localhost:8080/spring-mvc-simple/rss");
        feed.setTitle("Article Feed");
        feed.setDescription("Article Feed Description");
        feed.setPublishedDate(new Date());

        ArticleItem item1 = new ArticleItem();
        item1.setLink("http://www.baeldung.com/netty-exception-handling");
        item1.setTitle("Exceptions in Netty");
        item1.setDescription("In this quick article, we’ll be looking at exception handling in Netty.");
        item1.setPublishedDate(new Date());
        item1.setAuthor("Carlos");

        ArticleItem item2 = new ArticleItem();
        item2.setLink("http://www.baeldung.com/cockroachdb-java");
        item2.setTitle("Guide to CockroachDB in Java");
        item2.setDescription("This tutorial is an introductory guide to using CockroachDB with Java.");
        item2.setPublishedDate(new Date());
        item2.setAuthor("Baeldung");

        feed.addItem(item1);
        feed.addItem(item2);

        return feed;
    }

}
