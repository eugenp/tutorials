package com.baeldung.timer;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import javax.enterprise.event.Event;
import javax.inject.Inject;


@Startup
@Singleton
public class ScheduleTimerBean {

    @Inject
    Event<TimerEvent> event;

    @Schedule(hour = "*", minute = "*", second = "*/5", info = "Every 5 second timer")
    public void automaticallyScheduled(Timer timer) {
        fireEvent(timer);
    }


    private void fireEvent(Timer timer) {
        event.fire(new TimerEvent(timer.getInfo().toString()));
    }
}
