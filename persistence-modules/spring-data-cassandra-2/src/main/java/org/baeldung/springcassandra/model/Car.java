package org.baeldung.springcassandra.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Table
public class Car {

    @PrimaryKey
    private UUID id;

    private String make;

    private String model;

    private int year;

}
