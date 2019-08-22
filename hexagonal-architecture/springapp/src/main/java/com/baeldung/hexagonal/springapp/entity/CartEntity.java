package com.baeldung.hexagonal.springapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class CartEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name= "cart_id")
    private List<CartItemEntity> items;

    @OneToMany
    @JoinColumn(name= "cart_id")
    private List<CouponEntity> appliedCoupons;

    @Column
    private int effectivePrice;

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public List<CartItemEntity> getItems() {
        return items;
    }

    public void setItems(List<CartItemEntity> items) {
        this.items = items;
    }

    public int getEffectivePrice() {
        return effectivePrice;
    }

    public void setEffectivePrice(int effectivePrice) {
        this.effectivePrice = effectivePrice;
    }

    public List<CouponEntity> getAppliedCoupons() {
        return appliedCoupons;
    }

    public void setAppliedCoupons(List<CouponEntity> appliedCoupons) {
        this.appliedCoupons = appliedCoupons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
