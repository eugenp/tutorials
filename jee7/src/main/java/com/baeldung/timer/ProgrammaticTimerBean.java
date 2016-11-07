package com.baeldung.timer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * author: Jacek Jackowiak
 */
@Startup
@Singleton
public class ProgrammaticTimerBean {

    @Inject
    Event<TimerEvent> event;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void initialize() {
        ScheduleExpression scheduleExpression = new ScheduleExpression()
                .hour("*")
                .minute("*")
                .second("*/5");

        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo("Every 5 second timer");

        timerService.createCalendarTimer(scheduleExpression, timerConfig);
    }

    @Timeout
    public void programmaticTimout(Timer timer) {
        event.fire(new TimerEvent(timer.getInfo().toString()));
    }
}
