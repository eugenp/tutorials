package com.baeldung.spring.data.jpa.joinquery.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.time.LocalDate;

//@Entity
//@IdClass(DTO.class)
public class ResultDTO_wo_Ids {
    public ResultDTO_wo_Ids(String customerName, String customerEmail, LocalDate orderDate, String productName, Double productPrice) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.orderDate = orderDate;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    private String customerName;
    private String customerEmail;
    private LocalDate orderDate;
    private String productName;
    private Double productPrice;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

}
