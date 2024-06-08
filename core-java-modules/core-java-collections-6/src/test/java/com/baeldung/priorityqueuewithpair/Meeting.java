package com.baeldung.priorityqueuewithpair;

import java.time.LocalDateTime;
import java.util.Objects;

public class Meeting implements Comparable<Meeting> {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private final String title;

    public Meeting(LocalDateTime startTime, LocalDateTime endTime, String title) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Meeting meeting = (Meeting) object;

        if (!Objects.equals(startTime, meeting.startTime)) {
            return false;
        }
        if (!Objects.equals(endTime, meeting.endTime)) {
            return false;
        }
        return Objects.equals(title, meeting.title);
    }

    @Override
    public int hashCode() {
        int result = startTime != null ? startTime.hashCode() : 0;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Meeting meeting) {
        return this.startTime.compareTo(meeting.startTime);
    }
}
