package com.baeldung.interfaces;

import com.baeldung.interfaces.polymorphysim.Circle;
import com.baeldung.interfaces.polymorphysim.Shape;
import com.baeldung.interfaces.polymorphysim.Square;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PolymorphysimUnitTest {

    @Test
    public void whenInterfacePointsToCircle_CircleAreaMethodisBeingCalled(){
        double expectedArea = 12.566370614359172;
        Shape circle = new Circle(2);
        double actualArea = circle.area();
        Assertions.assertThat(actualArea).isEqualTo(expectedArea);
    }

    @Test
    public void whenInterfacePointsToSquare_SquareAreaMethodisBeingCalled(){
        double expectedArea = 4;
        Shape square = new Square(2);
        double actualArea = square.area();
        Assertions.assertThat(actualArea).isEqualTo(expectedArea);
    }
}
