package com.baeldung.scheduling.dynamic;

import org.springframework.stereotype.Service;

@Service
public class TickService {

    private long delay = 0;

    public long getDelay() {
        this.delay += 1000;
        System.out.println("delaying " + this.delay + " milliseconds...");
        return this.delay;
    }

    public void tick() {
        final long now = System.currentTimeMillis() / 1000;
        System.out
          .println("schedule tasks with dynamic delay - " + now);
    }

}
