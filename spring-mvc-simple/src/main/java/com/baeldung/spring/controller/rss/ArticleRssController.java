package com.baeldung.spring.controller.rss;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleRssController {

    @GetMapping(value = "/rss1")
    public String articleMvcFeed() {
        return "articleFeedView";
    }

    @GetMapping(value = "/rss2", produces = {"application/rss+xml", "application/rss+json"})
    @ResponseBody
    public Channel articleHttpFeed() {
        List<Article> items = new ArrayList<>();
        Article item1 = new Article();
        item1.setLink("http://www.baeldung.com/netty-exception-handling");
        item1.setTitle("Exceptions in Netty");
        item1.setDescription("In this quick article, we’ll be looking at exception handling in Netty.");
        item1.setPublishedDate(new Date());
        item1.setAuthor("Carlos");

        Article item2 = new Article();
        item2.setLink("http://www.baeldung.com/cockroachdb-java");
        item2.setTitle("Guide to CockroachDB in Java");
        item2.setDescription("This tutorial is an introductory guide to using CockroachDB with Java.");
        item2.setPublishedDate(new Date());
        item2.setAuthor("Baeldung");

        items.add(item1);
        items.add(item2);
        Channel channelData = buildChannel(items);

        return channelData;
    }

    private Channel buildChannel(List<Article> articles){
        Channel channel = new Channel("rss_2.0");
        channel.setLink("http://localhost:8080/spring-mvc-simple/rss");
        channel.setTitle("Article Feed");
        channel.setDescription("Article Feed Description");
        channel.setPubDate(new Date());

        List<Item> items = new ArrayList<>();
        for (Article article : articles) {
            Item item = new Item();
            item.setLink(article.getLink());
            item.setTitle(article.getTitle());
            Description description1 = new Description();
            description1.setValue(article.getDescription());
            item.setDescription(description1);
            item.setPubDate(article.getPublishedDate());
            item.setAuthor(article.getAuthor());
            items.add(item);
        }

        channel.setItems(items);
        return channel;
    }

}
