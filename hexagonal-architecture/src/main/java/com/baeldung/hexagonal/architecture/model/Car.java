package com.baeldung.hexagonal.architecture.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private Long id;
    private Mark mark;
    private Category category;
    private BigDecimal price;
    private Integer constructionYear;

}
