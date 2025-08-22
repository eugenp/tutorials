package com.baeldung.springai.om;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "User_Order")
public class OrderInfo   {
    @Id
    @Column(name = "order_id")
    private Long orderID;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "quantity")
    private Integer orderQuantity;

    public OrderInfo() {
        super();
    }

    public OrderInfo(Integer orderQuantity, String userID, Long orderID) {
        this.orderQuantity = orderQuantity;
        this.userID = userID;
        this.orderID = orderID;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
