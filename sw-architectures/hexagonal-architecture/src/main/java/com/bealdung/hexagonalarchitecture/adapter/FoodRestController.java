package com.bealdung.hexagonalarchitecture.adapter;

import com.bealdung.hexagonalarchitecture.core.Food;
import com.bealdung.hexagonalarchitecture.port.FoodService;
import com.bealdung.hexagonalarchitecture.web.FoodRestUI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/food")
@RestController
public class FoodRestController implements FoodRestUI {

    FoodService foodService;

    public FoodRestController(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public ResponseEntity createFood(Food food) {
        foodService.createFood(food);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/food/" + food.getName());
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Food> getFood(String name) {
        return new ResponseEntity<>(foodService.getFood(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Food>> listFood() {
        return new ResponseEntity<>(foodService.getAllFood(), HttpStatus.OK);
    }
}
