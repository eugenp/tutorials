package com.baeldung.springwebflux.model;

import lombok.*;

import java.io.Serializable;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equity {
    private int equityId;
    private double equityPrice;
}
