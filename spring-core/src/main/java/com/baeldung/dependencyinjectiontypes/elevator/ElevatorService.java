package com.baeldung.dependencyinjectiontypes.elevator;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElevatorService {

    private List<Elevator> elevators = new ArrayList<>();

    public int elevatorsCount(){
        return elevators.size();
    }
}
