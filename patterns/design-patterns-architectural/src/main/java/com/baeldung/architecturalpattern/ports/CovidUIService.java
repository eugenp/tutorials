package com.baeldung.architecturalpattern.ports;

import com.baeldung.architecturalpattern.dao.Covid;

public interface CovidUIService {
    
    public void updateStatus(String countryName, int activeCase, int recoveredCase, int fatalCase);

    public Covid getStatus(String countryName);

}
