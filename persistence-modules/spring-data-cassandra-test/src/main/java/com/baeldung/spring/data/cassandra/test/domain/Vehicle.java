package com.baeldung.spring.data.cassandra.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("vehicles")
@AllArgsConstructor
public class Vehicle {
    @Id
    private String vin;
    private Integer year;
    private String make;
    private String model;
}
