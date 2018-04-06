package eval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan("eval")
class Examples {

}

@Component
class FieldGarage {
    @Autowired
    private Car car;

    Car getCar() {
        return car;
    }
}

@Component
class MethodGarage {
    private Car car;

    @Autowired
    void setCar(Car car) {
        this.car = car;
    }

    Car getCar() {
        return car;
    }
}

@Component
class ConstructorGarage {
    private Car car;

    @Autowired
    ConstructorGarage(Car car) {
        this.car = car;
    }

    Car getCar() {
        return car;
    }
}

@Component
class Car {
}
