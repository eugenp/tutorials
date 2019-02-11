package com.baeldung.hexagonalarchitecture.tube;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Value
public class TubeTravel {

    String departureStation;
    String arrivalStation;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    Date departureTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    Date arrivalTime;
}
