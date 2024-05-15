package com.baeldung.timer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Timer;

class NewsletterTaskUnitManualTest {
    private final Timer timer = new Timer();

    @AfterEach
    void afterEach() {
        timer.cancel();
    }

    @Test
    void givenNewsletterTask_whenTimerScheduledEachSecondFixedDelay_thenNewsletterSentEachSecond() throws Exception {
        timer.schedule(new NewsletterTask(), 0, 1000);

        Thread.sleep(20000);
    }

    @Test
    void givenNewsletterTask_whenTimerScheduledEachSecondFixedRate_thenNewsletterSentEachSecond() throws Exception {
        timer.scheduleAtFixedRate(new NewsletterTask(), 0, 1000);

        Thread.sleep(20000);
    }
}