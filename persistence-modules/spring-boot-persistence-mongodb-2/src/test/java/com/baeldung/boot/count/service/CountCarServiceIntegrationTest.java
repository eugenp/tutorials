package com.baeldung.boot.count.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.count.SpringBootCountApplication;
import com.baeldung.boot.count.data.Car;

@SpringBootTest(classes = SpringBootCountApplication.class)
@DirtiesContext
@RunWith(SpringRunner.class)
@TestPropertySource("/embedded.properties")
public class CountCarServiceIntegrationTest {
    @Autowired
    private CountCarService service;

    Car car1 = new Car("B-A");

    @Before
    public void init() {
        service.insertCar(car1);
        service.insertCar(new Car("B-B"));
        service.insertCar(new Car("B-C"));
    }

    @Test
    public void givenAllDocs_whenQueryAnnotationCount_thenCountEqualsSize() {
        List<Car> all = service.findCars();

        long count = service.getCountWithQueryAnnotation();

        assertEquals(count, all.size());
    }

    @Test
    public void givenAllDocs_whenCrudRepositoryCount_thenCountEqualsSize() {
        List<Car> all = service.findCars();

        long count = service.getCountWithCrudRepository();

        assertEquals(count, all.size());
    }

    @Test
    public void givenFilteredDocs_whenCriteriaCountByBrand_thenCountEqualsSize() {
        String filter = "B-A";
        long all = service.findCars()
            .stream()
            .filter(car -> car.getBrand()
                .equals(filter))
            .count();

        long count = service.getCountBrandWithCriteria(filter);

        assertEquals(count, all);
    }

    @Test
    public void givenQueryAnnotation_whenCountingByBrand_thenCountEqualsSize() {
        String filter = "B-A";
        long all = service.findCars()
            .stream()
            .filter(car -> car.getBrand()
                .equals(filter))
            .count();

        long count = service.getCountBrandWithQueryAnnotation(filter);

        assertEquals(count, all);
    }

    @Test
    public void givenFilteredDocs_whenQueryMethodCountByBrand_thenCountEqualsSize() {
        String filter = "B-A";
        long all = service.findCars()
            .stream()
            .filter(car -> car.getBrand()
                .equals(filter))
            .count();

        long count = service.getCountBrandWithQueryMethod(filter);

        assertEquals(count, all);
    }

    @Test
    public void givenFilteredDocs_whenExampleCount_thenCountEqualsSize() {
        long all = service.findCars()
            .stream()
            .filter(car -> car.getBrand()
                .equals(car1.getBrand()))
            .count();

        long count = service.getCountWithExample(car1);

        assertEquals(count, all);
    }

    @Test
    public void givenFilteredDocs_whenExampleCriteriaCount_thenCountEqualsSize() {
        long all = service.findCars()
            .stream()
            .filter(car -> car.getBrand()
                .equals(car1.getBrand()))
            .count();

        long count = service.getCountWithExampleCriteria(car1);

        assertEquals(count, all);
    }
}
