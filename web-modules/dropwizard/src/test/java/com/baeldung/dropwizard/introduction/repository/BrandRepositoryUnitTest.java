package com.baeldung.dropwizard.introduction.repository;

import com.baeldung.dropwizard.introduction.domain.Brand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandRepositoryUnitTest {

    private static final Brand BRAND_1 = new Brand(1L, "Brand1");

    private final BrandRepository brandRepository  = new BrandRepository(getBrands());

    @Test
    void givenSize_whenFindingAll_thenReturnList() {
        final int size = 2;

        final List<Brand> result = brandRepository.findAll(size);

        assertEquals(size, result.size());
    }

    @Test
    void givenId_whenFindingById_thenReturnFoundBrand() {
        final Long id = BRAND_1.getId();

        final Optional<Brand> result = brandRepository.findById(id);

        Assertions.assertTrue(result.isPresent());
        assertEquals(BRAND_1, result.get());
    }


    private List<Brand> getBrands() {
        final List<Brand> brands = new ArrayList<>();
        brands.add(BRAND_1);
        brands.add(new Brand(2L, "Brand2"));
        brands.add(new Brand(3L, "Brand3"));

        return brands;
    }
}