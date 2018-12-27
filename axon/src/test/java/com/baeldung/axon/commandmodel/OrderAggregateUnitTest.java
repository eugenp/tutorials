package com.baeldung.axon.commandmodel;

import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.*;

import com.baeldung.axon.coreapi.commands.PlaceOrderCommand;
import com.baeldung.axon.coreapi.commands.ShipOrderCommand;
import com.baeldung.axon.coreapi.events.OrderConfirmedEvent;
import com.baeldung.axon.coreapi.events.OrderPlacedEvent;
import com.baeldung.axon.coreapi.events.OrderShippedEvent;

public class OrderAggregateUnitTest {

    private static final String ORDER_ID = UUID.randomUUID().toString();
    private static final String DEFAULT_PRODUCT = "Deluxe Chair";

    private FixtureConfiguration<OrderAggregate> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    public void giveNoPriorActivity_whenPlaceOrderCommand_thenShouldPublishOrderPlacedEvent() {
        fixture.givenNoPriorActivity()
               .when(new PlaceOrderCommand(ORDER_ID, DEFAULT_PRODUCT))
               .expectEvents(new OrderPlacedEvent(ORDER_ID, DEFAULT_PRODUCT));
    }

    @Test
    public void givenOrderPlacedEvent_whenShipOrderCommand_thenShouldThrowIllegalStateException() {
        fixture.given(new OrderPlacedEvent(ORDER_ID, DEFAULT_PRODUCT))
               .when(new ShipOrderCommand(ORDER_ID))
               .expectException(IllegalStateException.class);
    }

    @Test
    public void givenOrderPlacedEventAndOrderConfirmedEvent_whenShipOrderCommand_thenShouldPublishOrderShippedEvent() {
        fixture.given(new OrderPlacedEvent(ORDER_ID, DEFAULT_PRODUCT), new OrderConfirmedEvent(ORDER_ID))
               .when(new ShipOrderCommand(ORDER_ID))
               .expectEvents(new OrderShippedEvent(ORDER_ID));
    }

}