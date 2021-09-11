package com.baeldung.pattern.simplehexagonalexample.persistance;

import com.baeldung.pattern.simplehexagonalexample.domain.model.Furniture;

import java.util.List;

public interface FurnitureRepository {

    Furniture manufactureFurniture(Furniture furniture);

    Furniture getFurniture(String name);

    List<Furniture> listAllFurniture();
}
