package com.baeldung.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private Long id;
    private String customerName;
    private LocalDate orderDate;
}
