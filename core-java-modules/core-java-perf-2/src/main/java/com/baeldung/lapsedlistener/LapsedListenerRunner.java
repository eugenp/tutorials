package com.baeldung.lapsedlistener;

import static com.baeldung.lapsedlistener.UserGenerator.generateUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LapsedListenerRunner {

    private static final Logger logger = LoggerFactory.getLogger(LapsedListenerRunner.class);
    private static final MovieQuoteService movieQuoteService = new MovieQuoteService();

    static {
        movieQuoteService.start();
    }

public static void main(String[] args) {
    while (true) {
        User user = generateUser();
        logger.debug("{} logged in", user.getName());
        user.subscribe(movieQuoteService);
        userUsingService();
        logger.debug("{} logged out", user.getName());
    }
}

    private static void userUsingService() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
