package com.baeldung.zoneddatetime.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document
public class Action {

    @Id
    private String id;

    private String description;
    private ZonedDateTime time;

    public Action(String id, String description, ZonedDateTime time) {
        this.id = id;
        this.description = description;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Action{id='" + id + "', description='" + description + "', time=" + time + '}';
    }
}
