package com.baeldung.spring.data.jpa.naturalid.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.hibernate.annotations.NaturalId;

@Entity
public class ConferenceRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String name;

    private int capacity;

    public ConferenceRoom(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    protected ConferenceRoom() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "HotelRoom{" + "id=" + id + ", name='" + name + '\'' + ", capacity=" + capacity + '}';
    }
}
