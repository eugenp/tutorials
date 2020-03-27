package com.baeldung.architecturalpattern.adapters;

import com.baeldung.architecturalpattern.dao.Covid;
import com.baeldung.architecturalpattern.ports.CovidService;

public class CovidServiceImpl implements CovidService {

    private CovidRepoImpl repoImpl = new CovidRepoImpl();

    @Override
    public void updateStatus(String countryName, int activeCase, int recoveredCase, int fatalCase) {
        repoImpl.updateStatus(countryName, activeCase, recoveredCase, fatalCase);
    }

    @Override
    public Covid getStatus(String countryName) {
        return repoImpl.getStatus(countryName);
    }

}
