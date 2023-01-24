package com.baeldung.clone;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ShallowMatch implements Cloneable {

    private Type type;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    private Integer durationInMinutes;

    private PlayersDetail playersDetail;

    public enum Type {
        TENNIS, BADMINTON
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
