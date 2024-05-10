package com.baeldung.mapstruct.datetime.model;

import java.time.LocalDateTime;

/**
 * The Local order.
 */
public class LocalOrder {
    private Long id;
    private LocalDateTime created;
    //other fields


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
