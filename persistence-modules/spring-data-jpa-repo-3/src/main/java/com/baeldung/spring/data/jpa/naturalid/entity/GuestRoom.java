package com.baeldung.spring.data.jpa.naturalid.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.hibernate.annotations.NaturalId;

@Entity
public class GuestRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private Integer roomNumber;

    @NaturalId
    private Integer floor;

    private String name;
    private int capacity;

    public GuestRoom(int roomNumber, int floor, String name, int capacity) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.name = name;
        this.capacity = capacity;
    }

    protected GuestRoom() {
    }

    public Long getId() {
        return id;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GuestRoom{" + "id=" + id + ", roomNumber=" + roomNumber + ", floor=" + floor + ", name=" + name + ", capacity=" + capacity + '}';
    }
}
