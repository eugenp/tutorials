package com.baeldung.boot.composite.key.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndex(name = "sale_idx", def = "{ 'ticketId': 1 }", unique = true)
public class Sale {
    @Id
    private String id;

    private TicketId ticketId;
    private Double value;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public TicketId getTicketId() {
        return ticketId;
    }

    public void setTicketId(TicketId ticketId) {
        this.ticketId = ticketId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
