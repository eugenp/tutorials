package com.baeldung.limit;

import java.util.List;

interface CustomPassengerRepository {

    List<Passenger> findOrderedBySeatNumberLimitedTo(int limit);
}
