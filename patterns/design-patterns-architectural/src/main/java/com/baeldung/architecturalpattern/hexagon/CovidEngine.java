package com.baeldung.architecturalpattern.hexagon;

import com.baeldung.architecturalpattern.dao.Covid;

public interface CovidEngine {

    public void updateStatus(String countryName, int activeCase, int recoveredCase, int fatalCase);

    public Covid getStatus(String countryName);
    
}
