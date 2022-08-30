package com.baeldung.caching.ttl.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class City implements Serializable {
  private static final long serialVersionUID = 3252591505029724236L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private double cityCentreLatitude;
  private double cityCentreLongitude;

  public City() {}

  public City(Long id, String name, double cityCentreLatitude, double cityCentreLongitude) {
    this.id = id;
    this.name = name;
    this.cityCentreLatitude = cityCentreLatitude;
    this.cityCentreLongitude = cityCentreLongitude;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public double getCityCentreLatitude() {
    return cityCentreLatitude;
  }

  public double getCityCentreLongitude() {
    return cityCentreLongitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    City city = (City) o;

    if (Double.compare(city.cityCentreLatitude, cityCentreLatitude) != 0) return false;
    if (Double.compare(city.cityCentreLongitude, cityCentreLongitude) != 0) return false;
    if (!Objects.equals(id, city.id)) return false;
    return Objects.equals(name, city.name);
  }

}