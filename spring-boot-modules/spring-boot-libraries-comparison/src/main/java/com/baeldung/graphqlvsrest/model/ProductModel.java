package com.baeldung.graphqlvsrest.model;

import java.util.List;

public class ProductModel {
    private String name;
    private String description;
    private String status;
    private String currency;
    private Double price;
    private List<String> image_url;
    private List<String> video_url;
    private Integer stock;

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

    @Override
    public String toString() {
        return "ProductModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", image_url=" + image_url +
                ", video_url=" + video_url +
                ", stock=" + stock +
                '}';
    }
}
