package com.bealdung.hexagonal.port.inbound;

import com.bealdung.hexagonal.domain.Dessert;
import com.bealdung.hexagonal.port.outbound.DessertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DessertServiceImpl implements DessertService {

    @Autowired
    DessertRepository dessertRepository;

    @Override
    public void createDessert(Dessert dessert) {
        dessertRepository.createDessert(dessert);
    }

    @Override
    public Dessert getDessert(String dessertName) {
        return dessertRepository.getDessert(dessertName);
    }

    @Override
    public List<Dessert> listDessert() {
        return dessertRepository.listDessert();
    }

    public void setDessertRepository(DessertRepository dessertRepository) {
        this.dessertRepository = dessertRepository;
    }
}
