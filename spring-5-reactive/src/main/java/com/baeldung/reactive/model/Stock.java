package com.baeldung.reactive.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Stock {

    private float price;
    private String name;
    private Date date;

}
