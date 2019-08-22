package com.baeldung.hexagonal.core;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final Customer customer;

    private final List<CartItem> items;

    private final List<Coupon> appliedCoupons;

    private final int effectivePrice;

    public Cart(Customer customer) {
        this(customer, emptyList(), emptyList(), 0);
    }

    public Cart(Customer customer, List<CartItem> items, List<Coupon> appliedCoupons, int effectivePrice) {
        this.customer = customer;
        this.items = items;
        this.appliedCoupons = appliedCoupons;
        this.effectivePrice = effectivePrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public List<Coupon> getAppliedCoupons() {
        return appliedCoupons;
    }

    public Cart addItem(CartItem item) {
        List<CartItem> newItems = new ArrayList<>(items);
        newItems.add(item);
        return new Cart(customer, newItems, appliedCoupons, effectivePrice);
    }

    public int calculateBasePrice() {
        return items.stream().mapToInt(CartItem::calculateBasePrice).sum();
    }

    public Cart withAppliedCoupon(Coupon coupon) {
        List<Coupon> newAppliedCoupons = new ArrayList<>(appliedCoupons);
        newAppliedCoupons.add(coupon);
        return new Cart(customer, items, newAppliedCoupons, effectivePrice);
    }

    public Cart withEffectivePrice(int effectivePrice) {
        return new Cart(customer, items, appliedCoupons, effectivePrice);
    }
}
