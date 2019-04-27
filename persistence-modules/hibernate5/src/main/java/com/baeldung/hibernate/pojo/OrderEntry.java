package com.baeldung.hibernate.pojo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class OrderEntry {

    @EmbeddedId
    private OrderEntryPK entryId;

    public OrderEntryPK getEntryId() {
        return entryId;
    }

    public void setEntryId(OrderEntryPK entryId) {
        this.entryId = entryId;
    }

}
