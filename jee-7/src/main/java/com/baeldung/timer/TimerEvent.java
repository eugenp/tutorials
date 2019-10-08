package com.baeldung.timer;

public class TimerEvent {

    private String eventInfo;
    private long time = System.currentTimeMillis();

    public TimerEvent(String s) {
        this.eventInfo = s;
    }

    public long getTime() {
        return time;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    @Override
    public String toString() {
        return "TimerEvent {" +
            "eventInfo='" + eventInfo + '\'' +
            ", time=" + time +
            '}';
    }
}
