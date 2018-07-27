package com.baeldung.reactive.webflux;

import java.util.Date;

public class Event {

    private long id;

    private Date createdDate;

    public Event(long id, Date createdDate) {
        this.id = id;
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
