package com.baeldung.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LombokDestination {

    private String name;
    private String description;

}
