package com.baeldung.architecturalpattern.adapters;

import com.baeldung.architecturalpattern.dao.Covid;
import com.baeldung.architecturalpattern.hexagon.CovidEngine;
import com.baeldung.architecturalpattern.ports.CovidUIService;

public class CovidUIServiceImpl implements CovidUIService {

    private CovidEngine engineImpl;

    public CovidUIServiceImpl(CovidEngine serviceImpl) {
        this.engineImpl = serviceImpl;
    }

    @Override
    public void updateStatus(String countryName, int activeCase, int recoveredCase, int fatalCase) {
        engineImpl.updateStatus(countryName, activeCase, recoveredCase, fatalCase);

    }

    @Override
    public Covid getStatus(String countryName) {
        return engineImpl.getStatus(countryName);
    }

}
