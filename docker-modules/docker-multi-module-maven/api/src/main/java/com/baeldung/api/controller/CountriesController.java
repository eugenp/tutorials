package com.baeldung.api.controller;

import com.baeldung.domain.Country;
import com.baeldung.domain.CountryRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@RestController
public class CountriesController {

    private final CountryRepository countries;

    public CountriesController(CountryRepository countries) {
        this.countries = countries;
    }

    @GetMapping("api/countries")
    public List<CountryDto> index() {

        Iterable<Country> all = countries.findAll();
        return stream(all.spliterator(), false).map(it -> new CountryDto(it.getId(), it.getName(), it.getIso(), it.getEmoji()))
            .collect(toList());
    }

}
