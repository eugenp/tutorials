package com.baeldung.h2db.springboot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private BigDecimal price;
}
