package com.baeldung.hexagonalarchitecture.ports;

import java.util.List;

public interface IDiscountProvider {

    double getOrderPrice(List<Integer> productIds);
}
