package com.baeldung.associations.unidirectional;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ParkingSpot {

    @Id
    private Long id;

}
