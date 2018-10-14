package com.baeldung.rss;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;

/**
 * View for a RSS feed.
 * 
 * @author Donato Rimenti
 */
@Component
public class RssFeedView extends AbstractRssFeedView {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.view.feed.AbstractFeedView#
	 * buildFeedMetadata(java.util.Map, com.rometools.rome.feed.WireFeed,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
		feed.setTitle("Baeldung RSS Feed");
		feed.setDescription("Learn how to program in Java");
		feed.setLink("http://www.baeldung.com");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.view.feed.AbstractRssFeedView#
	 * buildFeedItems(java.util.Map, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {

		// Builds the single entries.
		Item entryOne = new Item();
		entryOne.setTitle("JUnit 5 @Test Annotation");
		entryOne.setAuthor("donatohan.rimenti@gmail.com");
		entryOne.setLink("http://www.baeldung.com/junit-5-test-annotation");
		entryOne.setPubDate(Date.from(Instant.parse("2017-12-19T00:00:00Z")));

		Item entryTwo = new Item();
		entryTwo.setTitle("Creating and Configuring Jetty 9 Server in Java");
		entryTwo.setAuthor("donatohan.rimenti@gmail.com");
		entryTwo.setLink("http://www.baeldung.com/jetty-java-programmatic");
		entryTwo.setPubDate(Date.from(Instant.parse("2018-01-23T00:00:00Z")));

		Item entryThree = new Item();
		entryThree.setTitle("Flyweight Pattern in Java");
		entryThree.setAuthor("donatohan.rimenti@gmail.com");
		entryThree.setLink("http://www.baeldung.com/java-flyweight");
		entryThree.setPubDate(Date.from(Instant.parse("2018-02-01T00:00:00Z")));

		Item entryFour = new Item();
		entryFour.setTitle("Multi-Swarm Optimization Algorithm in Java");
		entryFour.setAuthor("donatohan.rimenti@gmail.com");
		entryFour.setLink("http://www.baeldung.com/java-multi-swarm-algorithm");
		entryFour.setPubDate(Date.from(Instant.parse("2018-03-09T00:00:00Z")));

		Item entryFive = new Item();
		entryFive.setTitle("A Simple Tagging Implementation with MongoDB");
		entryFive.setAuthor("donatohan.rimenti@gmail.com");
		entryFive.setLink("http://www.baeldung.com/mongodb-tagging");
		entryFive.setPubDate(Date.from(Instant.parse("2018-03-27T00:00:00Z")));

		Item entrySix = new Item();
		entrySix.setTitle("Double-Checked Locking with Singleton");
		entrySix.setAuthor("donatohan.rimenti@gmail.com");
		entrySix.setLink("http://www.baeldung.com/java-singleton-double-checked-locking");
		entrySix.setPubDate(Date.from(Instant.parse("2018-04-23T00:00:00Z")));

		Item entrySeven = new Item();
		entrySeven.setTitle("Introduction to Dagger 2");
		entrySeven.setAuthor("donatohan.rimenti@gmail.com");
		entrySeven.setLink("http://www.baeldung.com/dagger-2");
		entrySeven.setPubDate(Date.from(Instant.parse("2018-06-30T00:00:00Z")));

		// Creates the feed.
		return Arrays.asList(entryOne, entryTwo, entryThree, entryFour, entryFive, entrySix, entrySeven);
	}

}