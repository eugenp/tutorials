package com.baeldung.hexagonal.architecture.domain.service;

import com.baeldung.hexagonal.architecture.domain.model.CovidCase;
import com.baeldung.hexagonal.architecture.port.CovidCaseRepository;
import com.baeldung.hexagonal.architecture.port.CovidCaseServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The class is an use case implementation of the inbound port.
 */
@Service
public class CovidCaseServicePortImplementation implements CovidCaseServicePort {

    @Autowired
    private CovidCaseRepository covidCaseRepository;

    @Override
    public List<CovidCase> getCovidCases() {
        return covidCaseRepository.getCovidCases();
    }

    @Override
    public CovidCase getCovidCaseById(Integer covidCaseId) {
        return covidCaseRepository.getCovidCaseById(covidCaseId);
    }

}
