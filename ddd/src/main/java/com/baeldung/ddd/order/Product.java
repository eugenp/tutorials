package com.baeldung.ddd.order;

import org.joda.money.Money;

public class Product {
    private final Money price;

    public Product(Money price) {
        super();
        this.price = price;
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
        Product other = (Product) obj;
        if (price == null) {
            if (other.price != null) {
                return false;
            }
        } else if (!price.equals(other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Product [price=" + price + "]";
    }

    Money getPrice() {
        return price;
    }

}
