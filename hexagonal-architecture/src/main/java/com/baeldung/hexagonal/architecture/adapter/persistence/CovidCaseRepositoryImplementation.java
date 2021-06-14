package com.baeldung.hexagonal.architecture.adapter.persistence;

import com.baeldung.hexagonal.architecture.domain.model.CovidCase;
import com.baeldung.hexagonal.architecture.port.CovidCaseRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interface defines an output adapter which is an implementation of the outbound port.
 */
@Repository
public class CovidCaseRepositoryImplementation implements CovidCaseRepository {

    private static final Map<Integer, CovidCase> covidCasesMap = new HashMap<>(0);

    @Override
    public List<CovidCase> getCovidCases() {
        return new ArrayList<CovidCase>(covidCasesMap.values());
    }

    @Override
    public CovidCase getCovidCaseById(Integer covidCaseId) {
        return covidCasesMap.get(covidCaseId);
    }
}
