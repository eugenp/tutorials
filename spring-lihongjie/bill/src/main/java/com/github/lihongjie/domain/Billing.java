package com.github.lihongjie.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.lihongjie.utils.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "billing")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "created_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "last_edit_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastEditAt;
    @Column(name = "last_edit_by")
    private Long lastEditBy;

    public Billing() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastEditAt() {
        return lastEditAt;
    }

    public void setLastEditAt(LocalDateTime lastEditAt) {
        this.lastEditAt = lastEditAt;
    }

    public Long getLastEditBy() {
        return lastEditBy;
    }

    public void setLastEditBy(Long lastEditBy) {
        this.lastEditBy = lastEditBy;
    }
}
