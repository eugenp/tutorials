package com.baeldung.pattern.simplehexagonalexample.domain.services;

import com.baeldung.pattern.simplehexagonalexample.domain.model.Furniture;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FurnitureService {

    Furniture manufactureFurniture(Furniture furniture);

    Furniture getFurniture(String name);

    List<Furniture> listAllFurniture();
}
