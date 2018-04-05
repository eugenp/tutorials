import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Examples {

}

@Component
class Garage {
    @Autowired
    private Car car;
}

@Component
class Garage2 {
    private Car car;

    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }
}

@Component
class Garage3 {
    private Car car;

    @Autowired
    Garage3(Car car) {
        this.car = car;
    }
}

@Component
class Car {
}
