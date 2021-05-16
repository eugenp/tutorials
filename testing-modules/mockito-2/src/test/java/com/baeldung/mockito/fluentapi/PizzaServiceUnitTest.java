package com.baeldung.mockito.fluentapi;

import com.baeldung.mockito.fluentapi.Pizza.PizzaBuilder;
import com.baeldung.mockito.fluentapi.Pizza.PizzaSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PizzaServiceUnitTest {

    @Mock
    private Pizza expectedPizza;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PizzaBuilder anotherbuilder;

    @Captor
    private ArgumentCaptor<String> stringCaptor;
    @Captor
    private ArgumentCaptor<Pizza.PizzaSize> sizeCaptor;

    @Test
    public void givenTraditonalMocking_whenServiceInvoked_thenPizzaIsBuilt() {
        PizzaBuilder nameBuilder = Mockito.mock(Pizza.PizzaBuilder.class);
        PizzaBuilder sizeBuilder = Mockito.mock(Pizza.PizzaBuilder.class);
        PizzaBuilder firstToppingBuilder = Mockito.mock(Pizza.PizzaBuilder.class);
        PizzaBuilder secondToppingBuilder = Mockito.mock(Pizza.PizzaBuilder.class);
        PizzaBuilder stuffedBuilder = Mockito.mock(Pizza.PizzaBuilder.class);
        PizzaBuilder willCollectBuilder = Mockito.mock(Pizza.PizzaBuilder.class);
        PizzaBuilder discountBuilder = Mockito.mock(Pizza.PizzaBuilder.class);

        PizzaBuilder builder = Mockito.mock(Pizza.PizzaBuilder.class);
        when(builder.name(anyString())).thenReturn(nameBuilder);
        when(nameBuilder.size(any(Pizza.PizzaSize.class))).thenReturn(sizeBuilder);
        when(sizeBuilder.withExtraTopping(anyString())).thenReturn(firstToppingBuilder);
        when(firstToppingBuilder.withStuffedCrust(anyBoolean())).thenReturn(stuffedBuilder);
        when(stuffedBuilder.withExtraTopping(anyString())).thenReturn(secondToppingBuilder);
        when(secondToppingBuilder.willCollect(anyBoolean())).thenReturn(willCollectBuilder);
        when(willCollectBuilder.applyDiscount(anyInt())).thenReturn(discountBuilder);
        when(discountBuilder.build()).thenReturn(expectedPizza);

        PizzaService service = new PizzaService(builder);
        assertEquals("Expected Pizza", expectedPizza, service.orderHouseSpecial());

        verify(builder).name(stringCaptor.capture());
        assertEquals("Pizza name: ", "Special", stringCaptor.getValue());

        verify(nameBuilder).size(sizeCaptor.capture());
        assertEquals("Pizza size: ", PizzaSize.LARGE, sizeCaptor.getValue());

    }

    @Test
    public void givenDeepStubs_whenServiceInvoked_thenPizzaIsBuilt() {
        Mockito.when(anotherbuilder.name(anyString())
            .size(any(Pizza.PizzaSize.class))
            .withExtraTopping(anyString())
            .withStuffedCrust(anyBoolean())
            .withExtraTopping(anyString())
            .willCollect(anyBoolean())
            .applyDiscount(anyInt())
            .build())
            .thenReturn(expectedPizza);

        PizzaService service = new PizzaService(anotherbuilder);
        assertEquals("Expected Pizza", expectedPizza, service.orderHouseSpecial());
    }

}
