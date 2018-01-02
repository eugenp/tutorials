package com.baeldung.dependencyinjectiontypes.elevatorExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ElevatorController {

    private ElevatorService elevatorService;

    @Autowired
    public ElevatorController (ElevatorService elevatorService){
        this.elevatorService= elevatorService;
    }

    public int getElevatorsCount(){
        return elevatorService.elevatorsCount();
    }
}
