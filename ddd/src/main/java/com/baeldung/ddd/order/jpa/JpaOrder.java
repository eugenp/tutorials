package com.baeldung.ddd.order.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_table")
class JpaOrder {
    private String currencyUnit;
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection(fetch = FetchType.EAGER)
    private final List<JpaOrderLine> orderLines;
    private BigDecimal totalCost;

    JpaOrder() {
        totalCost = null;
        orderLines = new ArrayList<>();
    }

    JpaOrder(List<JpaOrderLine> orderLines) {
        checkNotNull(orderLines);
        if (orderLines.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one order line item");
        }
        this.orderLines = new ArrayList<>(orderLines);
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
        JpaOrder other = (JpaOrder) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "JpaOrder [currencyUnit=" + currencyUnit + ", id=" + id + ", orderLines=" + orderLines + ", totalCost=" + totalCost + "]";
    }

    void addLineItem(JpaOrderLine orderLine) {
        checkNotNull(orderLine);
        orderLines.add(orderLine);
    }

    String getCurrencyUnit() {
        return currencyUnit;
    }

    Long getId() {
        return id;
    }

    List<JpaOrderLine> getOrderLines() {
        return new ArrayList<>(orderLines);
    }

    BigDecimal getTotalCost() {
        return totalCost;
    }

    void removeLineItem(int line) {
        orderLines.remove(line);
    }

    void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    private static void checkNotNull(Object par) {
        if (par == null) {
            throw new NullPointerException("Parameter cannot be null");
        }
    }
}
