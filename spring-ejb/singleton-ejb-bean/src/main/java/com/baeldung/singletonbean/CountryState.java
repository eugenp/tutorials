package com.baeldung.singletonbean;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CountryState {

    public List<String> getStates(String country);

}
