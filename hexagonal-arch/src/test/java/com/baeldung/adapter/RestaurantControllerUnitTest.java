package com.baeldung.adapter;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baeldung.models.Restaurant;
import com.baeldung.ports.RestaurantService;

public class RestaurantControllerUnitTest {
    
    @InjectMocks
    private RestaurantController restaurantController;
    
    @Mock
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveRestaurant() {
        ResponseEntity<Restaurant> savedRestaurant 
          = restaurantController.saveRestaurant(
            new Restaurant("12345", "Spring Curry", "Baeldung World"));
        assertNotNull(savedRestaurant);
        assertEquals(savedRestaurant.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void testGetAllRestaurantDetails() {
        ResponseEntity<List<Restaurant>> allRestaurantDetails 
          = restaurantController.getAllRestaurantDetails();
        assertNotNull(allRestaurantDetails);
        assertEquals(allRestaurantDetails.getStatusCode(), HttpStatus.OK);
    }

}
