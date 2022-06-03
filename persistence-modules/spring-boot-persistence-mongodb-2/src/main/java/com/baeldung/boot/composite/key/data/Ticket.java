package com.baeldung.boot.composite.key.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Ticket {
    @Id
    private TicketId id;

    private String event;

    public Ticket() {
    }

    public Ticket(TicketId id, String event) {
        super();
        this.id = id;
        this.event = event;
    }

    public TicketId getId() {
        return id;
    }

    public void setId(TicketId id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
