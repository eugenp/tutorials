package com.baeldung.dropwizard.introduction.repository;

import com.baeldung.dropwizard.introduction.domain.Brand;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BrandRepository {
    private final List<Brand> brands;

    public BrandRepository(final List<Brand> brands) {
        this.brands = ImmutableList.copyOf(brands);
    }

    public List<Brand> findAll(final int size) {
        return brands
          .stream()
          .limit(size)
          .collect(Collectors.toList());
    }

    public Optional<Brand> findById(final Long id) {
        return brands
          .stream()
          .filter(brand -> brand
            .getId()
            .equals(id))
          .findFirst();
    }
}
