package com.baeldung.spring.data.jpa.joinquery.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

class DTO {
    private Long customer_id;
    private Long order_id;
    private Long product_id;

    public DTO(Long customer_id, Long order_id, Long product_id) {
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.product_id = product_id;
    }
}

@Entity
@IdClass(DTO.class)
public class ResultDTO {
    @Id
    private Long customer_id;

    @Id
    private Long order_id;

    @Id
    private Long product_id;

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public ResultDTO(Long customer_id, Long order_id, Long product_id, String customerName, String customerEmail, LocalDate orderDate, String productName, Double productPrice) {
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.product_id = product_id;
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

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustoemr_id(Long custoemr_id) {
        this.customer_id = custoemr_id;
    }

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

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
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
