package com.baeldung.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.models.Restaurant;
import com.baeldung.ports.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> saveRestaurant(
          @RequestBody Restaurant restaurant) {
        return new ResponseEntity<>(restaurantService.saveRestaurant(restaurant), 
            HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurantDetails() {
        return new ResponseEntity<>(restaurantService.getRestaurantDetails(), 
            HttpStatus.OK);
    }
}
