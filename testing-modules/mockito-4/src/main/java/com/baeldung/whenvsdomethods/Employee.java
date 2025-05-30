package com.baeldung.whenvsdomethods;

import java.time.DayOfWeek;

public interface Employee {

    String greet();
    
    void work(DayOfWeek day);
    
}
