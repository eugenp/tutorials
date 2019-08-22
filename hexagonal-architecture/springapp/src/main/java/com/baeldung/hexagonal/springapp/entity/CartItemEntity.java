package com.baeldung.hexagonal.springapp.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItemEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private ProductEntity product;

    @Column
    private int quantity;

//    @ManyToOne
//    @JoinColumn(name = "cart_id")
//    private CartEntity cart;

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
//
//    public CartEntity getCart() {
//        return cart;
//    }
//
//    public void setCart(CartEntity cart) {
//        this.cart = cart;
//    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CartItemEntity))
            return false;
        CartItemEntity that = (CartItemEntity) o;
        return quantity == that.quantity &&
                Objects.equals(product, that.product);
    }

    @Override public int hashCode() {
        return Objects.hash(product, quantity);
    }
}
