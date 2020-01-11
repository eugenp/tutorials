package com.baeldung.domain;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.baeldung.models.Restaurant;
import com.baeldung.ports.RestaurantRepository;

public class RestaurantServiceImplUnitTest {
    
    @InjectMocks
    private RestaurantServiceImpl restaurantServiceImpl;
    
    @Mock
    private RestaurantRepository restaurantRepository;
    
    Restaurant restaurant;

    @BeforeEach
    public void setUp() throws Exception {
        restaurant 
        = new Restaurant("12345", "Spring Curry", "Baeldung's World");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveRestaurant() {
        Mockito
        .when(restaurantRepository.save(ArgumentMatchers.any(Restaurant.class)))
        .thenReturn(restaurant);
        
        Restaurant savedRestaurant 
          = restaurantServiceImpl
            .saveRestaurant(restaurant);
        assertNotNull(savedRestaurant);
    }

    @Test
    public void testGetRestaurantDetails() {
        List<Restaurant> restaurantDetails 
        = restaurantServiceImpl.getRestaurantDetails();
        assertNotNull(restaurantDetails);
    }

}
