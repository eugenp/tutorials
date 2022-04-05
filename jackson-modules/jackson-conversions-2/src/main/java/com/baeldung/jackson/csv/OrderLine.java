package com.baeldung.jackson.csv;

import java.math.BigDecimal;

public class OrderLine {
    private String item;
    private int quantity;
    private BigDecimal unitPrice;
    
    public OrderLine() {
        
    }

    public OrderLine(String item, int quantity, BigDecimal unitPrice) {
        super();
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderLine [item=" + item + ", quantity=" + quantity + ", unitPrice=" + unitPrice + "]";
    }
}
