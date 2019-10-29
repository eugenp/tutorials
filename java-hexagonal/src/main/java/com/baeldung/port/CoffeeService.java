package com.baeldung.port;

import com.baeldung.core.domain.Coffee;
import java.util.List;

public interface CoffeeService {

        public void createCoffee(Coffee coffee);

        public Coffee getCoffee(String name);

        public List<Coffee> listCoffee();

}
