package com.baeldung.hibernate.onetomany.collection.listvsset;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;

    public OrderEntity() {
    }

    public OrderEntity(String item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity order = (OrderEntity) o;
        return Objects.equals(item, order.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
}
