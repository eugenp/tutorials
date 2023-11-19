package com.baeldung.boot.jdbi.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarModel {
    private Long id;
    private String name;
    private Integer yearDate;
    private String sku;
    private Long makerId;
}
