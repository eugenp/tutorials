package com.baeldung.hibernate.pojo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderEntryPK implements Serializable {

    private long orderId;
    private long productId;

    @ManyToOne
    private User user;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderEntryPK pk = (OrderEntryPK) o;
        return Objects.equals(orderId, pk.orderId) && Objects.equals(productId, pk.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}