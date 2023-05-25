package com.baeldung.timer;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Date;


@Startup
@Singleton
public class AutomaticTimerBean {


    @Inject
    Event<TimerEvent> event;

    /**
     * This method will be called every 10 second and will fire an @TimerEvent
     */
    @Schedule(hour = "*", minute = "*", second = "*/10", info = "Every 10 second timer")
    public void printDate() {
        event.fire(new TimerEvent("TimerEvent sent at :" + new Date()));
    }

}
