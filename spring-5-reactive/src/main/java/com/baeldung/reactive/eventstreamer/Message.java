package com.baeldung.reactive.eventstreamer;

import java.util.Date;

/**
 * Created by Nakul on 23-06-2018.
 */
public class Message {
    private long id;
    private Date date;

    public Message() {
    }

    public Message(long id, Date date) {
        this.id = id;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", date=" + date + '}';
    }
}
