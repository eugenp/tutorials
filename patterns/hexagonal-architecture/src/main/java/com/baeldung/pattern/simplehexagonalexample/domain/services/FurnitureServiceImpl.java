package com.baeldung.pattern.simplehexagonalexample.domain.services;

import com.baeldung.pattern.simplehexagonalexample.domain.model.Furniture;
import com.baeldung.pattern.simplehexagonalexample.persistance.FurnitureRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FurnitureServiceImpl implements FurnitureService {

    private final FurnitureRepository furnitureRepository;

    @Autowired
    public FurnitureServiceImpl(FurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }

    @Override
    public Furniture manufactureFurniture(Furniture furniture) {
        return furnitureRepository.manufactureFurniture(furniture);
    }

    @Override
    public Furniture getFurniture(String name) {
        return furnitureRepository.getFurniture(name);
    }

    @Override
    public List<Furniture> listAllFurniture() {
        return furnitureRepository.listAllFurniture();
    }
}
