package com.baeldung.port;

import com.baeldung.core.domain.Coffee;
import java.util.List;

public interface CoffeeRepository {

        void createCoffee(Coffee coffee);

        Coffee getCoffee(String name);

        List<Coffee> getAllCoffee();

}
