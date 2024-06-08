package com.baeldung.mapstruct.datetime.model;

import java.time.Instant;

public class Order {
    private Long id;
    private Instant created;
    //other fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
