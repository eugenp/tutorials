package com.baeldung.ddd.order;

import org.joda.money.Money;

public class OrderLine {
    private final Product product;
    private final int quantity;

    public OrderLine(Product product, int quantity) {
        super();
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OrderLine other = (OrderLine) obj;
        if (product == null) {
            if (other.product != null) {
                return false;
            }
        } else if (!product.equals(other.product)) {
            return false;
        }
        if (quantity != other.quantity) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        result = prime * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "OrderLine [product=" + product + ", quantity=" + quantity + "]";
    }

    Money cost() {
        return product.getPrice()
            .multipliedBy(quantity);
    }

    Product getProduct() {
        return product;
    }

    int getQuantity() {
        return quantity;
    }

}
