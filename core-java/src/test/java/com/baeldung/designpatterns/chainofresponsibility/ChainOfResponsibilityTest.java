package com.baeldung.designpatterns.chainofresponsibility;

import org.junit.Test;

public class ChainOfResponsibilityTest {

    private static AbstractNumberHandler getChainOfNumberHandlers() {

        AbstractNumberHandler oddNumberHandler = new OddNumberHandler(null);
        AbstractNumberHandler evenNumberHandler = new EvenNumberHandler(oddNumberHandler);
        AbstractNumberHandler primeNumberHandler = new PrimeNumberHandler(evenNumberHandler);

        return primeNumberHandler;
    }

    @Test
    public void givenMessage_whenLogLevelDebug_thenMessageStartsWithDebugKeyword() {
        AbstractNumberHandler numberHandlerChain = getChainOfNumberHandlers();
        numberHandlerChain.handleNumber(13);
    }

    @Test
    public void givenMessage_whenLogLevelInfo_thenMessageStartsWithInfoKeyword() {
        AbstractNumberHandler numberHandlerChain = getChainOfNumberHandlers();
        numberHandlerChain.handleNumber(12);
    }

    @Test
    public void givenMessage_whenLogLevelError_thenMessageStartsWithErrorKeyword() {
        AbstractNumberHandler numberHandlerChain = getChainOfNumberHandlers();
        numberHandlerChain.handleNumber(15);
    }

}
