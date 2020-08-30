package com.baeldung.boot.jdbi.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarMaker {
    private Long id;
    private String name;
    private List<CarModel> models;
}
