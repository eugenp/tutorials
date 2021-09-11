package com.baeldung.pattern.simplehexagonalexample.persistance;

import com.baeldung.pattern.simplehexagonalexample.domain.model.Furniture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FurnitureRepositoryImpl implements FurnitureRepository {

    private final Map<String, Furniture> furnitureStore = new HashMap<>();

    @Override
    public Furniture manufactureFurniture(Furniture furniture) {
        return furnitureStore.put(furniture.getName(), furniture);
    }

    @Override
    public Furniture getFurniture(String name) {
        return furnitureStore.get(name);
    }

    @Override
    public List<Furniture> listAllFurniture() {
        return null;
    }
}
