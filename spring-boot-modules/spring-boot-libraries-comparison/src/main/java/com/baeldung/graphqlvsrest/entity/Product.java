package com.baeldung.graphqlvsrest.entity;

import com.baeldung.graphqlvsrest.model.ProductModel;

import java.util.List;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private String status;
    private String currency;
    private Double price;
    private List<String> image_url;
    private List<String> video_url;
    private Integer stock;
    private Float average_rating;

    public Product(Integer id, ProductModel productModel) {
        this.id = id;
        this.name = productModel.getName();
        this.description = productModel.getDescription();
        this.currency = productModel.getCurrency();
        this.price = productModel.getPrice();
        this.stock = productModel.getStock();
        this.image_url = productModel.getImage_url();
        this.video_url = productModel.getVideo_url();
        this.average_rating = 0F;
        this.status = productModel.getStatus();
    }

    public Product(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
    }

    public List<String> getVideo_url() {
        return video_url;
    }

    public void setVideo_url(List<String> video_url) {
        this.video_url = video_url;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(Float average_rating) {
        this.average_rating = average_rating;
    }
}
