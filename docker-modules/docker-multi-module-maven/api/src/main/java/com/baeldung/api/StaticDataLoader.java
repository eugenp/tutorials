package com.baeldung.api;

import com.baeldung.domain.Country;
import com.baeldung.domain.CountryRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class StaticDataLoader implements CommandLineRunner {

    private final CountryRepository countries;

    public StaticDataLoader(CountryRepository countries) {
        this.countries = countries;
    }

    @Override
    public void run(String... args) {
        countries.save(new Country(1L, "US", "United States", ":us:"));
        countries.save(new Country(2L, "CA", "Canada", ":canada:"));
        countries.save(new Country(3L, "GB", "United Kingdom", ":uk:"));
        countries.save(new Country(4L, "AU", "Romania", ":romania:"));
    }
}
