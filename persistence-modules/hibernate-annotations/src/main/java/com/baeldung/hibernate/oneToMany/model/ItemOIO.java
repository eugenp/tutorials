package com.baeldung.hibernate.oneToMany.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ITEMSOIO")
public class ItemOIO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private CartOIO cart;

    // Hibernate requires no-args constructor
    public ItemOIO() {
    }

    public ItemOIO(CartOIO c) {
        this.cart = c;
    }

    public CartOIO getCartOIO() {
        return cart;
    }

    public void setCartOIO(CartOIO cart) {
        this.cart = cart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
