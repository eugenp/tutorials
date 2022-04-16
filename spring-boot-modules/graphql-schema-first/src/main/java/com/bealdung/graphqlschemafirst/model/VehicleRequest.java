package com.bealdung.graphqlschemafirst.model;

import lombok.Data;

@Data
public class VehicleRequest {
    private String type;
    private String modelCode;
    private String brandName;
    private String launchDate;
}
