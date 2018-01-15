package com.baeldung.spliteratorAPI;

import java.util.Spliterator;
import java.util.logging.Logger;

public class Task implements Runnable {
	private final static Logger LOG = Logger.getLogger(Task.class.getName());
	private Spliterator<Article> spliterator;
	private final static String SUFFIX = "- published by Baeldung";

	public Task(Spliterator<Article> spliterator) {
		this.spliterator = spliterator;
	}

	@Override
	public void run() {
		int current = 0;
		while (spliterator.tryAdvance(article -> {
			article.setName(article.getName().concat(SUFFIX));
		})) {
			current++;
		}
		;
		LOG.info(Thread.currentThread().getName() + ":" + current);
	}
}
