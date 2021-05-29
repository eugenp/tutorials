package com.baeldung.hexagon.architecture.dao;

import com.baeldung.hexagon.architecture.entity.Country;

public interface CountryDaoInterface {
    Country getCountryByName(String name);
}
