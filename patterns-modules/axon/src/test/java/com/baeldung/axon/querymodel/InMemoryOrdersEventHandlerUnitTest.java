package com.baeldung.axon.querymodel;

public class InMemoryOrdersEventHandlerUnitTest extends AbstractOrdersEventHandlerUnitTest {

    @Override
    protected OrdersEventHandler getHandler() {
        return new InMemoryOrdersEventHandler(emitter);
    }
}
