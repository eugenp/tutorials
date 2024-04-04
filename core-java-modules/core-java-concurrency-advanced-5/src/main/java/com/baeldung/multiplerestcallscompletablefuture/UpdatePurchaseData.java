package com.baeldung.multiplerestcallscompletablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;

public class UpdatePurchaseData {

    public void upsertPurchases(List<Purchase> purchases) {
        purchases.forEach(this::updatePurchase);
    }

    public void updatePurchase(Purchase purchase) {
        CompletableFuture.allOf(
                CompletableFuture.
                        supplyAsync(() -> getOrderDescription("order_id"))
                        .thenAccept(purchase::setOrderDescription),
                CompletableFuture.
                        supplyAsync(() -> getPaymentDescription("payment_id"))
                        .thenAccept(purchase::setPaymentDescription),
                CompletableFuture.
                        supplyAsync(() -> getUserName("user_id"))
                        .thenAccept(purchase::setBuyerName)
        ).join();
    }

    public String getOrderDescription(String orderId) {
        // Implementation of a rest call
        sleep(500);
        return "Order description from REST call";
    }

    public String getPaymentDescription(String purchaseId) {
        // Implementation of a rest call
        sleep(500);
        return "Purchase description from REST call";
    }

    public String getUserName(String userId) {
        // Implementation of a rest call
        sleep(10000);
        return "User name from REST call";
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {

        }
    }

    private static BiFunction<Void, Throwable, ?> handleGracefully() {
        return (result, exception) -> {
            if (exception != null) {
                if (exception instanceof TimeoutException) {
                    // handle timeouts
                }
                // handle other exceptions
                return null;
            }
            return result;
        };
    }
}
