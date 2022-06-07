package com.baeldung.graphql.error.handling.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    private String zipcode;

    private String city;
    private String state;

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();
}
