package com.baeldung.springwebflux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equity {
    private int equityId;
    private double equityPrice;
}
