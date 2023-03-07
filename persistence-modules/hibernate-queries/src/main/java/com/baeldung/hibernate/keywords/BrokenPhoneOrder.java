package com.baeldung.hibernate.keywords;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "broken_phone_order")
public class BrokenPhoneOrder implements Serializable {
    @Id
    @Column(name = "order")
    String order;
    @Column(name = "where")
    String where;

    public BrokenPhoneOrder(String order, String where) {
        this.order = order;
        this.where = where;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }
}
