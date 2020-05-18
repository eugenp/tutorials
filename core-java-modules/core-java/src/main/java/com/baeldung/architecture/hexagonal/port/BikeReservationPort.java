package com.baeldung.architecture.hexagonal.port;

import com.baeldung.architecture.hexagonal.domain.Bike;
import com.baeldung.architecture.hexagonal.domain.Category;
import com.baeldung.architecture.hexagonal.domain.Size;

public interface BikeReservationPort {
    Bike processReservationRequest(Category category, Size size);
}
