package com.hexagonal.demo.ports.secondary;

import com.hexagonal.demo.domain.Area;

public interface AreaPortRepository {

    Area getArea(String zipCode);
}
