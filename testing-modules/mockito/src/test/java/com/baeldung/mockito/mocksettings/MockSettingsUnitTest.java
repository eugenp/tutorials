package com.baeldung.mockito.mocksettings;

import static org.mockito.Answers.RETURNS_SMART_NULLS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.exceptions.verification.SmartNullPointerException;
import org.mockito.junit.MockitoJUnitRunner;

import com.baeldung.mockito.fluentapi.Pizza;
import com.baeldung.mockito.fluentapi.PizzaService;

@RunWith(MockitoJUnitRunner.class)
public class MockSettingsUnitTest {

    @Test(expected = SmartNullPointerException.class)
    public void whenServiceMockedWithSmartNulls_thenExceptionHasExtraInfo() {
        PizzaService service = mock(PizzaService.class, withSettings().defaultAnswer(RETURNS_SMART_NULLS));
        Pizza pizza = service.orderHouseSpecial();
        pizza.getSize();
    }

    @Test
    public void whenServiceMockedWithNameAndVerboseLogging_thenLogsMethodInvocations() {
        PizzaService service = mock(PizzaService.class, withSettings().name("pizzaServiceMock")
            .verboseLogging());

        Pizza pizza = mock(Pizza.class);
        when(service.orderHouseSpecial()).thenReturn(pizza);

        service.orderHouseSpecial();

        verify(service).orderHouseSpecial();
    }

    @Test
    public void whenServiceMockedWithExtraInterfaces_thenConstructorSuccess() {
        SpecialInterface specialMock = mock(SpecialInterface.class, withSettings().extraInterfaces(Runnable.class));
        new SimpleService(specialMock);
    }

    @Test
    public void whenMockSetupWithConstructor_thenConstructorIsInvoked() {
        AbstractCoffee coffeeSpy = mock(AbstractCoffee.class, withSettings().useConstructor("espresso")
            .defaultAnswer(CALLS_REAL_METHODS));

        assertEquals("Coffee name: ", "espresso", coffeeSpy.getName());
    }

}
