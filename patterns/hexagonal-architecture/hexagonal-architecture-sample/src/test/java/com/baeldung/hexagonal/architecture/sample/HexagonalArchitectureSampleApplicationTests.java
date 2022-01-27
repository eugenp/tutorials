package com.baeldung.hexagonal.architecture.sample;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.hexagonal.architecture.sample.model.Car;
import com.baeldung.hexagonal.architecture.sample.repository.CarRepositoryImpl;
import com.baeldung.hexagonal.architecture.sample.services.CarServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HexagonalArchitectureSampleApplicationTests {
	private static CarServiceImpl carService;
	static Car testCar;

	@BeforeAll
	static void init() {
		CarRepositoryImpl repository = new CarRepositoryImpl();
		carService = new CarServiceImpl();
		carService.setCarRepository(repository);
		Car car = new Car();
		car.setId("123");
		car.setManufacturer("BMW");
		car.setPlate("110TGV");
		testCar = car;
		carService.addCar(car);
	}

	@Test
	void givenCarId_whenGetPlate_thenReturnPlate() {
		String plate = testCar.getPlate();

		assertEquals(plate, carService.getPlate("123"));
	}

}
