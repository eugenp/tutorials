package com.baeldung.clone;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ShallowMatch implements Cloneable {

    private TYPE type;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    private Integer durationInMinutes;

    private PlayersDetail playersDetail;

    public enum TYPE {
        TENNIS, BADMINTON
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
