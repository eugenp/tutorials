package com.baeldung.hexagonal.architecture.port;


import com.baeldung.hexagonal.architecture.domain.model.CovidCase;

import java.util.List;

/**
 * The repository interface is an outbound port that enables communication from the core application to a database
 */
public interface CovidCaseRepository {

    List<CovidCase> getCovidCases();

    CovidCase getCovidCaseById(Integer covidCaseId);

}
