package com.baeldung.dddhexagonalquickexample.domain;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CarStore {
    @Id
    private Long id;
    private String address;
    @OneToMany(mappedBy="store", fetch=FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Car> catalog;
    private Double money;

    public Car getCar(Long id) {
        Optional<Car> car = catalog.stream().filter(c -> c.getId().equals(id)).findFirst();
        return car.get();
    }

    public void sellCar(Long id) {
        Optional<Car> car = catalog.stream().filter(c -> c.getId().equals(id)).findFirst();
        catalog.remove(car.get());
        this.money += car.get().getPrice();
    }

    public void addCarToCatalog(Car car) {
        catalog.add(car);
    }	

    // Getters and Setters
    public Long getId() {
        return id;
    }	
    public void setId(Long id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<Car> getCatalog() {
        return catalog;
    }
    public void setCatalog(List<Car> catalog) {
        this.catalog = catalog;
    }
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
}
