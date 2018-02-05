package com.baeldung.rss;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("articleFeedView")
public class ArticleFeedView extends AbstractRssFeedView {

    protected Channel newFeed() {
        Channel channel = new Channel("rss_2.0");
        channel.setLink("http://localhost:8080/rss");
        channel.setTitle("Article Feed");
        channel.setDescription("Article Feed Description");
        channel.setPubDate(new Date());
        return channel;
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> map, HttpServletRequest httpStRequest, HttpServletResponse httpStResponse) throws Exception {
        List list = new ArrayList<Item>();

        Item item1 = new Item();
        item1.setLink("http://www.baeldung.com/netty-exception-handling");
        item1.setTitle("Exceptions in Netty");
        Description description1 = new Description();
        description1.setValue("In this quick article, we’ll be looking at exception handling in Netty.");
        item1.setDescription(description1);
        item1.setPubDate(new Date());
        item1.setAuthor("Carlos");

        Item item2 = new Item();
        item2.setLink("http://www.baeldung.com/cockroachdb-java");
        item2.setTitle("Guide to CockroachDB in Java");
        Description description2 = new Description();
        description2.setValue("This tutorial is an introductory guide to using CockroachDB with Java.");
        item2.setDescription(description2);
        item2.setPubDate(new Date());
        item2.setAuthor("Baeldung");

        list.add(item1);
        list.add(item2);
        return list;
    }
}
