package com.baeldung.singleton;

import java.util.UUID;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class CarServiceSingleton {

    private static Logger LOG = LoggerFactory.getLogger(CarServiceSingleton.class);

    private UUID id = UUID.randomUUID();

    private static int serviceQueue;

    public UUID getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CarServiceSingleton [id=" + id + "]";
    }

    public int service(Car car) {
        serviceQueue++;
        LOG.info("Car {} is being serviced @ CarServiceSingleton - serviceQueue: {}", car, serviceQueue);
        simulateService(car);
        serviceQueue--;
        LOG.info("Car service for {} is completed - serviceQueue: {}", car, serviceQueue);
        return serviceQueue;
    }

    private void simulateService(Car car) {
        try {
            Thread.sleep(100);
            car.setServiced(true);
        } catch (InterruptedException e) {
            LOG.error("CarServiceSingleton::InterruptedException: {}", e);
        }
    }

}
