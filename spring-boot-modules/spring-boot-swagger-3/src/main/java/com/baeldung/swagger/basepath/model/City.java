package com.baeldung.swagger.basepath.model;

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

  public void setName(String name) {
    this.name = name;
  }

  public double getCityCentreLatitude() {
    return cityCentreLatitude;
  }

  public void setCityCentreLatitude(double cityCentreLatitude) {
    this.cityCentreLatitude = cityCentreLatitude;
  }

  public double getCityCentreLongitude() {
    return cityCentreLongitude;
  }

  public void setCityCentreLongitude(double cityCentreLongitude) {
    this.cityCentreLongitude = cityCentreLongitude;
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

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    temp = Double.doubleToLongBits(cityCentreLatitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(cityCentreLongitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long id;
    private String name;
    private double cityCentreLatitude;
    private double cityCentreLongitude;

    public Builder setId(Long id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setCityCentreLatitude(double cityCentreLatitude) {
      this.cityCentreLatitude = cityCentreLatitude;
      return this;
    }

    public Builder setCityCentreLongitude(double cityCentreLongitude) {
      this.cityCentreLongitude = cityCentreLongitude;
      return this;
    }

    public City build() {
      return new City(id, name, cityCentreLatitude, cityCentreLongitude);
    }
  }
}
