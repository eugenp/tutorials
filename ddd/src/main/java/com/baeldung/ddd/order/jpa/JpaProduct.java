package com.baeldung.ddd.order.jpa;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
class JpaProduct {
    private String currencyUnit;
    private BigDecimal price;

    public JpaProduct() {
    }

    public JpaProduct(BigDecimal price, String currencyUnit) {
        super();
        this.price = price;
        currencyUnit = currencyUnit;
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
        JpaProduct other = (JpaProduct) obj;
        if (currencyUnit == null) {
            if (other.currencyUnit != null) {
                return false;
            }
        } else if (!currencyUnit.equals(other.currencyUnit)) {
            return false;
        }
        if (price == null) {
            if (other.price != null) {
                return false;
            }
        } else if (!price.equals(other.price)) {
            return false;
        }
        return true;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currencyUnit == null) ? 0 : currencyUnit.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        return result;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "JpaProduct [currencyUnit=" + currencyUnit + ", price=" + price + "]";
    }
}
