package com.baeldung.jackson.yaml;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderNo;
    private LocalDate date;
    private String customerName;
    private List<OrderLine> orderLines;

    public Order() {

    }

    public Order(String orderNo, LocalDate date, String customerName, List<OrderLine> orderLines) {
        super();
        this.orderNo = orderNo;
        this.date = date;
        this.customerName = customerName;
        this.orderLines = orderLines;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderLine> getOrderLines() {
        if (orderLines == null) {
            orderLines = new ArrayList<>();
        }
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null) {
            orderLines = new ArrayList<>();
        }
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "Order [orderNo=" + orderNo + ", date=" + date + ", customerName=" + customerName + ", orderLines=" + orderLines + "]";
    }

}
