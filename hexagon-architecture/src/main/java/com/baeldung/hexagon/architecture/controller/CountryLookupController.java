package com.baeldung.hexagon.architecture.controller;

import com.baeldung.hexagon.architecture.entity.CountryResponse;
import com.baeldung.hexagon.architecture.service.CountryLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryLookupController {

    private CountryLookupService countryLookupService;

    @Autowired
    public CountryLookupController(CountryLookupService countryLookupService) {
        this.countryLookupService = countryLookupService;
    }

    @GetMapping("/name")
    CountryResponse getCountryByName(@RequestParam String name) {
        return countryLookupService.getCountryByName(name);
    }

}
