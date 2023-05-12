package com.baeldung.hibernate.keywords;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "phone_order")
public class PhoneOrder implements Serializable {
    @Id
    @Column(name = "`order`")
    String order;
    @Column(name = "`where`")
    String where;

    public PhoneOrder(String order, String where) {
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
