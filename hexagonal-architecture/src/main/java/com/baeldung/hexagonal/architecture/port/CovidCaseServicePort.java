package com.baeldung.hexagonal.architecture.port;


import com.baeldung.hexagonal.architecture.domain.model.CovidCase;

import java.util.List;

/**
 * The interface is an inbound port provides the flow and the application functionality to the outside
 */
public interface CovidCaseServicePort {

    List<CovidCase> getCovidCases();

    CovidCase getCovidCaseById(Integer covidCaseId);

}
