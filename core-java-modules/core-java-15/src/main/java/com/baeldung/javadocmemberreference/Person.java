package com.baeldung.javadocmemberreference;

import com.baeldung.sealed.records.Car;

public class Person {
    Person() {

    }

    /**
     * Also, check the {@link #move() Move} method for more movement details.
     */
    public void walk() {

    }

    /**
     * Check this {@link #move(String) Move} method for direction oriented movement.
     */
    public void move() {

    }

    public void move(String direction) {

    }

    /**
     * Additionally, check this {@link Animal#run(String) Run} method for direction based run.
     */
    public void run() {

    }

    /**
     * Also consider checking {@link com.baeldung.sealed.classes.Vehicle#Vehicle() Vehicle} constructor to initialize vehicle object.
     */
    public void goToWork() {

    }

    /**
     * Have a look at {@link Car#getNumberOfSeats() SeatsAvailability} method for checking the available seats needed for driving.
     */
    public void drive() {

    }
}
