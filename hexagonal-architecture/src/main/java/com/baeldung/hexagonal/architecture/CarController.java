package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.model.Car;
import com.baeldung.hexagonal.architecture.model.Category;
import com.baeldung.hexagonal.architecture.model.Mark;
import com.baeldung.hexagonal.architecture.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private CarService rentCarService;

    @Autowired
    public CarController(CarService rentCarService) {
        this.rentCarService = rentCarService;
    }

    @GetMapping(path = "/cars")
    public ResponseEntity<List<Car>> searchCars(
            @RequestParam("mark") final Mark mark,
            @RequestParam("category") final Category category,
            @RequestParam("price") final BigDecimal price,
            @RequestParam("constructionYear") final Integer constructionYear) {
        List<Car> cars = rentCarService.searchCar(mark, category, price, constructionYear);
        return ResponseEntity.ok(cars);
    }
}
