package com.baeldung.graphql.error.handling.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    private String vin;
    private Integer year;
    private String make;
    private String model;
    private String trim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_location")
    private Location location;
}
