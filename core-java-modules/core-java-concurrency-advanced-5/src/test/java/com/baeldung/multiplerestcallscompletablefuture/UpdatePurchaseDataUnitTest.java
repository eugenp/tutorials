package com.baeldung.multiplerestcallscompletablefuture;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

class UpdatePurchaseDataUnitTest {

    @Test
    void givenContexts_whenAllTasksCompletesCorrectly_thenPopulateDataProperly() {
        List<Purchase> purchases = List.of(new Purchase(),
                new Purchase(), new Purchase());

        UpdatePurchaseData subject = spy(new UpdatePurchaseData());
        subject.upsertPurchases(purchases);

        System.out.println(purchases);
    }

    @Test
    void givenContexts_whenOneTaskCompletesExceptionally_thenGracefullyHandleException() {
        List<Purchase> purchases = List.of(new Purchase(),
                new Purchase(), new Purchase());

        UpdatePurchaseData subject = new UpdatePurchaseData();
        subject.upsertPurchases(purchases);

        System.out.println(purchases);
    }

    @Test
    void givenContexts_whenOneTaskCompletesWithTimeout_thenGracefullyHandleException() {
        List<Purchase> purchases = List.of(new Purchase(),
                new Purchase(), new Purchase());

        UpdatePurchaseData subject = new UpdatePurchaseData();
        subject.upsertPurchases(purchases);

        System.out.println(purchases);
    }
}