package com.baeldung.swagger.basepath.service;

import com.baeldung.swagger.basepath.model.City;

import java.util.List;

public interface CityService {
  List<City> getAllCities();

  City getCityById(Long id);

  City createCity(City city);
}
