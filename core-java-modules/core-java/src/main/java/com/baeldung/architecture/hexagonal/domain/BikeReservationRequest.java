package com.baeldung.architecture.hexagonal.domain;

public class BikeReservationRequest {
    private Category category;
    private Size size;

    public BikeReservationRequest(Category category, Size size) {
        this.category = category;
        this.size = size;
    }

    Size getSize() {
        return size;
    }

    Category getCategory() {
        return category;
    }
}
