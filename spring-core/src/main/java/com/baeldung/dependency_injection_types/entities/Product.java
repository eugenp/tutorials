package com.baeldung.dependency_injection_types.entities;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Product {

    private String code;
    private String name;
    private BigDecimal price;

}
