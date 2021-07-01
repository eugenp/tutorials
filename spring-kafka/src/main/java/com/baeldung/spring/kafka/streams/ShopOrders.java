package com.baeldung.spring.kafka.streams;

public class ShopOrders {

    private Long shop;
    private long count;

    public ShopOrders(final Long shop, final long count) {
        this.shop = shop;
        this.count = count;
    }

    public Long getShop() {
        return shop;
    }

    public void setShop(final Long shop) {
        this.shop = shop;
    }

    public long getCount() {
        return count;
    }

    public void setCount(final long count) {
        this.count = count;
    }
}