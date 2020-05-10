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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CarModel> getModels() {
        return models;
    }

    public void setModels(List<CarModel> models) {
        this.models = models;
    }
}
