package com.baeldung.springwebflux.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@Data
public class Equity {
    private final int equityId;
    private final double equityPrice;
}
