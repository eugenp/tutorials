package com.baeldung.dependencyinjectiontypes.elevator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Elevator {

    @Autowired
    @Qualifier("specialSchedule")
    private Schedule schedule;

    //or through setter
    private Schedule yetAnotherSchedule;

    @Autowired
    public void setSchedule(Schedule schedule) {
        this.yetAnotherSchedule = schedule;
    }
}
