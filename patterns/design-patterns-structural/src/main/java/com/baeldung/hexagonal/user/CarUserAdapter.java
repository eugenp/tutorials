package com.baeldung.hexagonal.user;

import com.baeldung.hexagonal.domain.CarPortUserSide;

public class CarUserAdapter {

    private CarPortUserSide carPortUserSide;

    public CarUserAdapter(CarPortUserSide carPortUserSide) {
        this.carPortUserSide = carPortUserSide;
    }
}
