package com.baeldung.jacocoexclusions.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
    private int id;
    private String name;

}
