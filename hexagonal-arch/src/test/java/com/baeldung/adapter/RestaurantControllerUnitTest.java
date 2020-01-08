//package com.baeldung.adapter;
//
//import static org.junit.Assert.assertNotEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import com.baeldung.models.Restaurant;
//import com.baeldung.ports.RestaurantService;
//
//public class RestaurantControllerUnitTest {
//    
//    @InjectMocks
//    RestaurantController restaurantController;
//    
//    RestaurantService restaurantService;
//
//    @Before
//    public void setUp() throws Exception {
//        restaurantService = Mockito.mock(RestaurantService.class);
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void shouldGetAllRestauarnts() {
//        
//        List<Restaurant> value = new ArrayList<>();
//        value.add(new Restaurant("12345", "Our Kitchen", "Baledung"));
//        Mockito.when(restaurantService.getRestaurantDetails()).thenReturn(value);
//        
//        ResponseEntity<List<Restaurant>> allRestaurantDetails 
//        = restaurantController.getAllRestaurantDetails();
//        assertNotEquals(allRestaurantDetails.getStatusCode(), HttpStatus.OK);
//    }
//
//}
