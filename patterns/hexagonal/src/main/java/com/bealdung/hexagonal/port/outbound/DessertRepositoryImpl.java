package com.bealdung.hexagonal.port.outbound;

import com.bealdung.hexagonal.domain.Dessert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DessertRepositoryImpl implements DessertRepository {
    static List<Dessert> dessertList = new ArrayList<>();
    static {
        Dessert dessert1 = new Dessert();
        dessert1.setDessertName("almond cookie");
        Dessert dessert2 = new Dessert();
        dessert2.setDessertName("baklava");
        dessertList.add(dessert1);
        dessertList.add(dessert2);
    }


    @Override
    public void createDessert(Dessert dessert)
    {
        dessertList.add(dessert);
    }

    @Override
    public Dessert getDessert(String dessertName)
    {
        Dessert dessert = new Dessert();
        dessert.setDessertName(dessertName);
        return dessert;
    }

    @Override
    public List<Dessert> listDessert()
    {
        return dessertList;
    }
}
