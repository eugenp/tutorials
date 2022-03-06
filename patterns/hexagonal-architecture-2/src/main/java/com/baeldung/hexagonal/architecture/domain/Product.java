package com.baeldung.hexagonal.architecture.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @JsonProperty("productId")
    private Integer productId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("description")
    private String description;

}
