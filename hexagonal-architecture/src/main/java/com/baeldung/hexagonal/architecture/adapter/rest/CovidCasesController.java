package com.baeldung.hexagonal.architecture.adapter.rest;


import com.baeldung.hexagonal.architecture.domain.model.CovidCase;
import com.baeldung.hexagonal.architecture.port.CovidCaseServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The class defines a REST controller which is a primary adapter and invokes the inbound port of the application.
 */
@RestController
@RequestMapping("/api/v1/covidcases")
public class CovidCasesController {

    private CovidCaseServicePort covidCaseServicePort;

    @Autowired
    public CovidCasesController(CovidCaseServicePort covidCaseServicePort) {
        this.covidCaseServicePort = covidCaseServicePort;
    }

    @GetMapping
    public ResponseEntity<List<CovidCase>> getCovidCases() {
        return new ResponseEntity<>(covidCaseServicePort.getCovidCases(), HttpStatus.OK);
    }

    @GetMapping("/{covidCaseId}")
    public ResponseEntity<CovidCase> getCovidCaseById(@PathVariable Integer covidCaseId) {
        return new ResponseEntity<>(covidCaseServicePort.getCovidCaseById(covidCaseId), HttpStatus.OK);
    }

}
