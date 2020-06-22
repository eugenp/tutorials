package com.baeldung.rome;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RSSRomeExample {

    public static void main(String[] args) throws IOException, FeedException {
        SyndFeed feed = createFeed();
        addEntryToFeed(feed);
        publishFeed(feed);
        readFeed();
    }

    private static SyndFeed createFeed() {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_1.0");
        feed.setTitle("Test title");
        feed.setLink("http://www.somelink.com");
        feed.setDescription("Basic description");

        return feed;
    }

    private static void addEntryToFeed(SyndFeed feed) {
        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle("Entry title");
        entry.setLink("http://www.somelink.com/entry1");

        addDescriptionToEntry(entry);
        addCategoryToEntry(entry);

        feed.setEntries(Arrays.asList(entry));
    }

    private static void addDescriptionToEntry(SyndEntry entry) {
        SyndContent description = new SyndContentImpl();
        description.setType("text/html");
        description.setValue("First entry");

        entry.setDescription(description);
    }

    private static void addCategoryToEntry(SyndEntry entry) {
        List<SyndCategory> categories = new ArrayList<>();
        SyndCategory category = new SyndCategoryImpl();
        category.setName("Sophisticated category");
        categories.add(category);

        entry.setCategories(categories);
    }

    private static void publishFeed(SyndFeed feed) throws IOException, FeedException {
        Writer writer = new FileWriter("xyz.txt");
        SyndFeedOutput syndFeedOutput = new SyndFeedOutput();
        syndFeedOutput.output(feed, writer);
        writer.close();
    }

    private static SyndFeed readFeed() throws IOException, FeedException {
        URL feedSource = new URL("http://rssblog.whatisrss.com/feed/");
        SyndFeedInput input = new SyndFeedInput();
        return input.build(new XmlReader(feedSource));
    }
}