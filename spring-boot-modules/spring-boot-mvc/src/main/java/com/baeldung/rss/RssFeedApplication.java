package com.baeldung.rss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot launcher for an application which exposes an RSS Feed.
 * 
 * @author Donato Rimenti
 *
 */
@SpringBootApplication
public class RssFeedApplication {

	/**
	 * Launches a Spring Boot application which exposes an RSS Feed.
	 * 
	 * @param args null
	 */
    public static void main(final String[] args) {
        SpringApplication.run(RssFeedApplication.class, args);
    }
}