package com.baeldung.rss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

/**
 * REST Controller which returns an RSS Feed created by {@link RssFeedView}.
 * 
 * @author Donato Rimenti
 *
 */
@RestController
public class RssFeedController {

	/**
	 * View used by this controller.
	 */
	@Autowired
	private RssFeedView view;

	/**
	 * Returns an RSS Feed created by {@link #view}.
	 * 
	 * @return an RSS Feed
	 */
	@GetMapping("/rss")
	public View getFeed() {
		return view;
	}
}
