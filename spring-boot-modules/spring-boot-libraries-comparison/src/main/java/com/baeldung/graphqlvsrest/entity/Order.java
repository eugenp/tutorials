package com.baeldung.graphqlvsrest.entity;

public class Order {
    private Integer id;
    private Integer product_id;
    private String customer_uuid;
    private String status;
    private String address;
    private String creation_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomer_uuid() {
        return customer_uuid;
    }

    public void setCustomer_uuid(String customer_uuid) {
        this.customer_uuid = customer_uuid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
