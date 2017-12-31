package com.baeldung.timer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * author: Cristian Chiovari
 */
@Startup
@Singleton
public class ProgrammaticAtFixedRateTimerBean {

    @Inject
    Event<TimerEvent> event;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void initialize() {
        timerService.createTimer(0,1000, "Every second timer");
    }

    @Timeout
    public void programmaticTimout(Timer timer) {
        event.fire(new TimerEvent(timer.getInfo().toString()));
    }
}
