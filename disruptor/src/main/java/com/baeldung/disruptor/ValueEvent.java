package com.baeldung.disruptor;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lmax.disruptor.EventFactory;

public final class ValueEvent {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public final static EventFactory<ValueEvent> EVENT_FACTORY = () -> new ValueEvent();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
