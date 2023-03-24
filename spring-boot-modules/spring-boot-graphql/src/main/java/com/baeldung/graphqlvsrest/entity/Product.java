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
    private List<String> imageUrls;
    private List<String> videoUrls;
    private Integer stock;
    private Float averageRating;

    public Product(Integer id, ProductModel productModel) {
        this.id = id;
        this.name = productModel.getName();
        this.description = productModel.getDescription();
        this.currency = productModel.getCurrency();
        this.price = productModel.getPrice();
        this.stock = productModel.getStock();
        this.imageUrls = productModel.getImageUrls();
        this.videoUrls = productModel.getVideoUrls();
        this.averageRating = 0F;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }
}
