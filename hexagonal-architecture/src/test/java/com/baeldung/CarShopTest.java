package com.baeldung;

import com.baeldung.adapters.outgoing.InMemoryCarStorageAdapter;
import com.baeldung.domain.facade.CarRentalService;
import com.baeldung.domain.ports.dtos.Car;
import com.baeldung.domain.ports.dtos.NewCar;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;


public class CarShopTest {

    private CarRentalService carRentalService;

    @Before
    public void init() {
        carRentalService = new CarRentalService(new InMemoryCarStorageAdapter());

    }

    @Test
    public void whenPutUpCarForSale_thenShouldBeVisibleOnAvailables() {
        List<Car> availableBefore = carRentalService.showAvailableCars();
        NewCar newCar = new NewCar("Focus", "Ford", BigDecimal.valueOf(10));
        carRentalService.putUpForSale(newCar);
        List<Car> availableAfterPuttingOnSale = carRentalService.showAvailableCars();

        Assert.assertThat(availableBefore, IsEmptyCollection.empty());
        Assert.assertThat(availableAfterPuttingOnSale, IsCollectionWithSize.hasSize(1));
        Car availableCar = availableAfterPuttingOnSale.get(0);
        Assert.assertEquals("Focus", availableCar.getName());
        Assert.assertEquals("Ford", availableCar.getBrand());
        Assert.assertEquals(10, availableCar.getMinimumPrice().intValue());
        Assert.assertNotNull(availableCar.getCarId());
    }

    @Test
    public void whenTryingToBuyTheCarBelowMinimumPrice_thenShouldNotSucceed() {
        NewCar newCar = new NewCar("V50", "Volvo", BigDecimal.valueOf(15));
        carRentalService.putUpForSale(newCar);
        List<Car> availableCars = carRentalService.showAvailableCars();
        Car carToBuy = availableCars.get(0);
        boolean ifSucceeded = carRentalService.buyCar(carToBuy.getCarId(), BigDecimal.valueOf(10));

        Assert.assertFalse(ifSucceeded);
        Assert.assertThat(carRentalService.showAvailableCars(), IsCollectionWithSize.hasSize(1));
    }

    @Test
    public void whenTryingToBuyTheCarWithEnoughMoney_thenShouldSucceed() {
        NewCar newCar = new NewCar("V50", "Volvo", BigDecimal.valueOf(15));
        carRentalService.putUpForSale(newCar);
        List<Car> availableCars = carRentalService.showAvailableCars();
        Car carToBuy = availableCars.get(0);
        boolean ifSucceeded = carRentalService.buyCar(carToBuy.getCarId(), BigDecimal.valueOf(16));

        Assert.assertTrue(ifSucceeded);
        Assert.assertThat(carRentalService.showAvailableCars(), IsEmptyCollection.empty());
    }
}
