package com.baeldung.h2db.notnull.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private BigDecimal price;
}
