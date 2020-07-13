package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.adapter.CarRepository;
import com.baeldung.architecture.hexagonal.adapter.CarRestController;
import com.baeldung.architecture.hexagonal.core.domain.Car;
import com.baeldung.architecture.hexagonal.core.impl.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
@ContextConfiguration(classes = {CarRestController.class, CarService.class, CarRepository.class})
public class CarRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void givenCar_WhenPost_ThenShouldCreateTheCar() throws Exception {
        // Given
        Car car = new Car();

        // Then
        mockMvc.perform(
                post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CarTestUtil.convertCarToJsonString(car)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAValidCarId_WhenGetById_ThenReturnTheCar() throws Exception {
        // Given
        int id = 1;
        Car car = new Car(1, "Benz");
        carRepository.createCar(car);

        // Then
        mockMvc.perform(get("/cars/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Benz"));
    }

    @Test
    public void givenSomeCars_WhenGetCars_ThenReturnListOfCars() throws Exception {
        // Given
        Car car = new Car(1, "Benz");
        Car car2 = new Car(2, "BMW");
        carRepository.createCar(car);
        carRepository.createCar(car2);

        // Then
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[1].id").value(1))
                .andExpect(jsonPath("$.[1].name").value("Benz"))
                .andExpect(jsonPath("$.[2].id").value(2))
                .andExpect(jsonPath("$.[2].name").value("BMW"));
    }
}