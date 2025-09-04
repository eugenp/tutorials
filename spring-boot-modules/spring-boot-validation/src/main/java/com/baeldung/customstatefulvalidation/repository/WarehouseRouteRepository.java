package com.baeldung.customstatefulvalidation.repository;

import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Repository
public class WarehouseRouteRepository {
    private Set<String> availableRoutes = Stream.of(
            "Springfield:USA",
            "Hartley:USA",
            "Gentoo:PL",
            "Mercury:GR")
            .collect(toSet());

    public boolean isWarehouseRouteAvailable(String sourceWarehouse, String destinationCountry) {
        return availableRoutes.contains(sourceWarehouse + ":" + destinationCountry);
    }
}
