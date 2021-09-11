package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.simplehexagonalexample.domain.model.Furniture;
import com.baeldung.pattern.simplehexagonalexample.domain.services.FurnitureService;
import com.baeldung.pattern.simplehexagonalexample.domain.services.FurnitureServiceImpl;
import com.baeldung.pattern.simplehexagonalexample.persistance.FurnitureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FurnitureServiceImplUnitTest {

    private final List<Furniture> testModelList = new ArrayList<>();
    private FurnitureRepository furnitureRepository;
    private FurnitureService testService;
    private Furniture testModel1;
    private Furniture testModel2;

    @BeforeEach
    void setUp() {
        furnitureRepository = mock(FurnitureRepository.class);

        testService = new FurnitureServiceImpl(furnitureRepository);
        testModel1 = new Furniture();
        testModel1.setName("bed");
        testModel1.setColor("brown");
        testModel1.setLength(50);
        testModel1.setBreadth(40);

        testModel2 = new Furniture();
        testModel2.setName("sofa");
        testModel2.setColor("brown");
        testModel2.setLength(50);
        testModel2.setBreadth(20);

        testModelList.add(testModel1);
        testModelList.add(testModel2);
    }

    @Test
    void manufactureFurniture() {
        when(furnitureRepository.manufactureFurniture(any(Furniture.class))).thenReturn(testModel1);

        Furniture testResponse = testService.manufactureFurniture(testModel1);
        assertEquals(testModel1, testResponse);
    }

    @Test
    void getFurniture() {
        when(furnitureRepository.getFurniture("bed")).thenReturn(testModel1);

        Furniture testResponse = testService.getFurniture("bed");
        assertEquals(testModel1, testResponse);
    }

    @Test
    void listAllFurniture() {
        when(furnitureRepository.listAllFurniture()).thenReturn(Arrays.asList(testModel1, testModel2));

        List<Furniture> testResponse = testService.listAllFurniture();
        assertEquals(testModelList, testResponse);
    }
}
