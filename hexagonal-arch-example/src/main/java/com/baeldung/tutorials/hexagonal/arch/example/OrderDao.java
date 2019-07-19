package com.baeldung.tutorials.hexagonal.arch.example;

public interface OrderDao {
    void save(int unitCount);
    Order get(int id);
}