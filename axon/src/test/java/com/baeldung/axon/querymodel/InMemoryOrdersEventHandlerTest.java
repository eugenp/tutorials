package com.baeldung.axon.querymodel;

public class InMemoryOrdersEventHandlerTest extends AbstractOrdersEventHandlerTest{

    @Override
    protected OrdersEventHandler getHandler() {
        return new InMemoryOrdersEventHandler(emitter);
    }
}
