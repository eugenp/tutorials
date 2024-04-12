package com.baeldung.maven.dependency.classifier.provider.stub;

import com.baeldung.maven.dependency.classifier.provider.model.Car;
import com.baeldung.maven.dependency.classifier.provider.model.PowerSource;
import com.baeldung.maven.dependency.classifier.provider.model.Car.Type;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class CarStub {
    public static Car ELECTRIC_CAR = Mockito.mock(Car.class);

    static {
        when(ELECTRIC_CAR.getType()).thenReturn(Type.ELECTRIC);
        when(ELECTRIC_CAR.getPowerSource()).thenReturn(PowerSource.BATTERY);
    }
}
