package com.baeldung.rome;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

public class RSSRomeExample {

    public static void main(String[] args) throws IOException, FeedException, URISyntaxException {
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

    private static SyndFeed readFeed() throws IOException, FeedException, URISyntaxException {
        URL feedSource = new URI("http://rssblog.whatisrss.com/feed/").toURL();
        SyndFeedInput input = new SyndFeedInput();
        return input.build(new XmlReader(feedSource));
    }
}