package com.baeldung.swagger.basepath.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Hotel implements Serializable {
  private static final long serialVersionUID = 5560221391479816650L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Double rating;

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private City city;

  private String address;
  private double latitude;
  private double longitude;
  private boolean deleted = false;

  public Hotel() {}

  public Hotel(
      Long id,
      String name,
      Double rating,
      City city,
      String address,
      double latitude,
      double longitude,
      boolean deleted) {
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.city = city;
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;
    this.deleted = deleted;
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

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Hotel hotel = (Hotel) o;

    if (Double.compare(hotel.latitude, latitude) != 0) return false;
    if (Double.compare(hotel.longitude, longitude) != 0) return false;
    if (deleted != hotel.deleted) return false;
    if (!Objects.equals(id, hotel.id)) return false;
    if (!Objects.equals(name, hotel.name)) return false;
    if (!Objects.equals(rating, hotel.rating)) return false;
    if (!Objects.equals(city, hotel.city)) return false;
    return Objects.equals(address, hotel.address);
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (rating != null ? rating.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    temp = Double.doubleToLongBits(latitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(longitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (deleted ? 1 : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long id;
    private String name;
    private Double rating;
    private City city;
    private String address;
    private double latitude;
    private double longitude;
    private boolean deleted;

    public Builder setId(Long id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setRating(Double rating) {
      this.rating = rating;
      return this;
    }

    public Builder setCity(City city) {
      this.city = city;
      return this;
    }

    public Builder setAddress(String address) {
      this.address = address;
      return this;
    }

    public Builder setLatitude(double latitude) {
      this.latitude = latitude;
      return this;
    }

    public Builder setLongitude(double longitude) {
      this.longitude = longitude;
      return this;
    }

    public Builder setDeleted(boolean deleted) {
      this.deleted = deleted;
      return this;
    }

    public Hotel build() {
      return new Hotel(id, name, rating, city, address, latitude, longitude, deleted);
    }
  }
}
