package com.baeldung.architecturalpattern.hexagon;

import com.baeldung.architecturalpattern.dao.Covid;
import com.baeldung.architecturalpattern.ports.CovidRepo;

public class CovidEngineImpl implements CovidEngine {

    private CovidRepo repoImpl;

    public CovidEngineImpl(CovidRepo repoImpl) {
        this.repoImpl = repoImpl;
    }

    @Override
    public void updateStatus(String countryName, int activeCase, int recoveredCase, int fatalCase) {
        repoImpl.updateStatus(countryName, activeCase, recoveredCase, fatalCase);
    }

    @Override
    public Covid getStatus(String countryName) {
        return repoImpl.getStatus(countryName);
    }

}
