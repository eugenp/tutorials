package com.baeldung.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Store {

    private Boolean active;
    @Id
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "STORE_ID")
    private List<Item> items = new ArrayList<>();
    private Long itemsSold;

    @ManyToOne
    private Location location;

    private String name;

    public Boolean getActive() {
        return active;
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public Long getItemsSold() {
        return itemsSold;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setItemsSold(Long itemsSold) {
        this.itemsSold = itemsSold;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }
}
