package com.baeldung.spring.controller.rss;

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleRssController {

    @GetMapping(value = "/rssMvc")
    public String articleMvcFeed() {
        return "articleFeedView";
    }

    @GetMapping(value = "/rssRest", produces = "application/rss+xml")
    @ResponseBody
    public String articleRestFeed() throws FeedException {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setLink("http://localhost:8080/spring-mvc-simple/rss");
        feed.setTitle("Article Feed");
        feed.setDescription("Article Feed Description");
        feed.setPublishedDate(new Date());

        List list = new ArrayList<SyndEntry>();

        SyndEntry item1 = new SyndEntryImpl();
        item1.setLink("http://www.baeldung.com/netty-exception-handling");
        item1.setTitle("Exceptions in Netty");
        SyndContent description1 = new SyndContentImpl();
        description1.setValue("In this quick article, we’ll be looking at exception handling in Netty.");
        item1.setDescription(description1);
        item1.setPublishedDate(new Date());
        item1.setAuthor("Carlos");

        SyndEntry item2 = new SyndEntryImpl();
        item2.setLink("http://www.baeldung.com/cockroachdb-java");
        item2.setTitle("Guide to CockroachDB in Java");
        SyndContent description2 = new SyndContentImpl();
        description2.setValue("This tutorial is an introductory guide to using CockroachDB with Java.");
        item2.setDescription(description2);
        item2.setPublishedDate(new Date());
        item2.setAuthor("Baeldung");

        list.add(item1);
        list.add(item2);
        feed.setEntries(list);

        return new SyndFeedOutput().outputString(feed);
    }

}
