package com.baeldung.ddd.order.jpa;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
class JpaOrderLine {
    @Embedded
    private final JpaProduct product;
    private final int quantity;

    JpaOrderLine() {
        quantity = 0;
        product = null;
    }

    JpaOrderLine(JpaProduct product, int quantity) {
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
        JpaOrderLine other = (JpaOrderLine) obj;
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
        return "JpaOrderLine [product=" + product + ", quantity=" + quantity + "]";
    }

    JpaProduct getProduct() {
        return product;
    }

    int getQuantity() {
        return quantity;
    }

}
