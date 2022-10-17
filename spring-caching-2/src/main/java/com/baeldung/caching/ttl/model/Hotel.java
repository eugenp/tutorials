package com.baeldung.caching.ttl.model;

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
  private double lattitude;
  private double longitude;
  private boolean deleted = false;

  public Hotel() {}

  public Hotel(
      Long id,
      String name,
      Double rating,
      City city,
      String address,
      double lattitude,
      double longitude,
      boolean deleted) {
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.city = city;
    this.address = address;
    this.lattitude = lattitude;
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
    return lattitude;
  }

  public void setLatitude(double latitude) {
    this.lattitude = latitude;
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

    if (Double.compare(hotel.lattitude, lattitude) != 0) return false;
    if (Double.compare(hotel.longitude, longitude) != 0) return false;
    if (deleted != hotel.deleted) return false;
    if (!Objects.equals(id, hotel.id)) return false;
    if (!Objects.equals(name, hotel.name)) return false;
    if (!Objects.equals(rating, hotel.rating)) return false;
    if (!Objects.equals(city, hotel.city)) return false;
    return Objects.equals(address, hotel.address);
  }

}