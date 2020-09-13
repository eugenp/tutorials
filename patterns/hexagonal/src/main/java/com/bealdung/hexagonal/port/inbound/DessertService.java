package com.bealdung.hexagonal.port.inbound;

import com.bealdung.hexagonal.domain.Dessert;


import java.util.List;


public interface DessertService {
    public void createDessert(Dessert dessert);

    public Dessert getDessert(String dessertName);

    public List<Dessert> listDessert();
}
