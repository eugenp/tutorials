package com.baeldung.lapsedlistener;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MovieQuoteServiceUnitTest {

    @Test
    void whenSubscribeToService_thenServiceHasOneSubscriber() {
        MovieQuoteService service = new MovieQuoteService();
        service.attach(UserGenerator.generateUser());
        assertEquals(1, service.numberOfSubscribers());
    }

    @Test
    void whenUnsubscribeFromService_thenServiceHasNoSubscribers() {
        MovieQuoteService service = new MovieQuoteService();
        User user = UserGenerator.generateUser();
        service.attach(user);
        service.detach(user);
        assertEquals(0, service.numberOfSubscribers());
    }
}