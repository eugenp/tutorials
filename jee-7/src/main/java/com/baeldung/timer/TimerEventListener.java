package com.baeldung.timer;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class will listen to all TimerEvent and will collect them
 */
@Startup
@Singleton
public class TimerEventListener {

    final List<TimerEvent> events = new CopyOnWriteArrayList<>();

    public void listen(@Observes TimerEvent event) {
        System.out.println("event = " + event);
        events.add(event);
    }

    public List<TimerEvent> getEvents() {
        return events;
    }
}
