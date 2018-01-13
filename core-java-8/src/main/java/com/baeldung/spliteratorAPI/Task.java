package com.baeldung.spliteratorAPI;

import java.util.Spliterator;

public class Task implements Runnable {
    private Spliterator<Article> spliterator;
    private final static String SUFFIX = "- published by Baeldung";

    public Task(Spliterator<Article> spliterator) {
        this.spliterator = spliterator;
    }

    @Override
    public void run() {
        int current = 0;
        while (spliterator.tryAdvance(article -> {
            article.setName(article.getName()
                .concat(SUFFIX));
        })) {
            current++;
        }
        ;
        System.out.println(Thread.currentThread()
            .getName() + ":" + current);
    }
}
