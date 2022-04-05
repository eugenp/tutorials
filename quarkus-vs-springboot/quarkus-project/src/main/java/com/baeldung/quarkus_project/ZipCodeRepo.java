package com.baeldung.quarkus_project;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ZipCodeRepo implements PanacheRepositoryBase<ZipCode, String> {

    public Multi<ZipCode> findByCity(String city) {
        return find("city = ?1", city).stream();
    }

    public Uni<ZipCode> save(ZipCode zipCode) {
        return zipCode.persistAndFlush();
    }
}
