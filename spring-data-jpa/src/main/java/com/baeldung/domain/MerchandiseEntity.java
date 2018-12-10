package com.baeldung.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class MerchandiseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private BigDecimal price;

    private String brand;

    public MerchandiseEntity() {
    }

    public MerchandiseEntity(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "MerchandiseEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                '}';
    }
}
