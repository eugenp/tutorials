package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PriorityEnum {

    LOW(0), MEDIUM(1), HIGH(3);

    @JsonValue
    private int level;

    PriorityEnum(int level) {
        this.level = level;
    }

}
