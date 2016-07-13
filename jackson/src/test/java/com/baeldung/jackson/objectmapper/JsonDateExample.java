package com.baeldung.jackson.objectmapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDateExample extends Example {

    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonDateExample() {
    }

    @Override
    public String name() {
        return this.getClass().getName();
    }

    @Override
    public void execute() {
        Logger.debug("Executing: " + name());
        try {
            final Car car = new Car("yellow", "renault");
            final Request request = new Request();
            request.setCar(car);
            request.setDatePurchased(new Date());
            final ObjectMapper objectMapper = new ObjectMapper();
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            objectMapper.setDateFormat(df);
            final String carAsString = objectMapper.writeValueAsString(request);
            Logger.debug(carAsString);
        } catch (final Exception e) {
            Logger.error(e.toString());
        }
    }
    class Request {
        Car car;
        Date datePurchased;
        public Car getCar() {
            return car;
        }

        public void setCar(final Car car) {
            this.car = car;
        }

        public Date getDatePurchased() {
            return datePurchased;
        }

        public void setDatePurchased(final Date datePurchased) {
            this.datePurchased = datePurchased;
        }
    }
}
