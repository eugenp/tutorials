package com.bealdung.hexagonal.port.outbound;

import com.bealdung.hexagonal.domain.Dessert;

import java.util.List;

public interface DessertRepository {
    public void createDessert(Dessert dessert);

    public Dessert getDessert(String dessertName);

    public List<Dessert> listDessert();
}
