package com.baeldung.pattern.architecture.hexagonal.model;

import lombok.Data;

@Data
public class Product {
    Long id;
    String name;
    Long price;
}
