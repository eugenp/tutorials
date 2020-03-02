package com.baeldung.hexagonal.architecture.application.adapters.input;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.architecture.application.dtos.BreweryDto;
import com.baeldung.hexagonal.architecture.core.ports.BreweryPort;

@RestController
public class BreweryController {

    @Autowired
    private BreweryPort breweryService;

    @GetMapping(path = "/breweries")
    List<BreweryDto> search(@RequestParam("city") String city) throws IOException, InterruptedException {
        return breweryService.search(city);
    }

}