package com.baeldung.lapsedlistener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovieQuoteService implements Subject {

    private static final Logger logger = LoggerFactory.getLogger(MovieQuoteService.class);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final List<Observer> observers = new ArrayList<>();
    private final Faker faker = new Faker();

    @Override
    public void attach(Observer observer) {
        logger.debug("Current number of subscribed users: {}", observers.size());
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        String quote = faker.movie().quote();
        logger.debug("New quote: {}", quote);
        for (Observer observer : observers) {
            logger.debug("Notifying user: {}", observer);
            observer.update(quote);
        }
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::notifyObservers, 0, 1, TimeUnit.SECONDS);
    }

    public int numberOfSubscribers() {
        return observers.size();
    }
}