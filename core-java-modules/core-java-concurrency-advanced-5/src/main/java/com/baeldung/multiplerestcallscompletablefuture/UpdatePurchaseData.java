package com.baeldung.multiplerestcallscompletablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;

public class UpdatePurchaseData {

    public void updatePurchases(List<Purchase> purchases) {
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

    public void updatePurchaseHandlingExceptions(Purchase purchase) {
        CompletableFuture.allOf(
                CompletableFuture.
                        supplyAsync(() -> getOrderDescription("order_id"))
                        .thenAccept(purchase::setOrderDescription)
                        .orTimeout(5, TimeUnit.SECONDS)
                        .handle(handleGracefully()),
                CompletableFuture.
                        supplyAsync(() -> getPaymentDescription("payment_id"))
                        .thenAccept(purchase::setPaymentDescription)
                        .orTimeout(5, TimeUnit.SECONDS)
                        .handle(handleGracefully()),
                CompletableFuture.
                        supplyAsync(() -> getUserName("user_id"))
                        .thenAccept(purchase::setBuyerName)
                        .orTimeout(5, TimeUnit.SECONDS)
                        .handle(handleGracefully())
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

    private static BiFunction<Void, Throwable, Void> handleGracefully() {
        return (result, exception) -> {
            if (exception != null) {
                // handle exception
            }
            return result;
        };
    }
}
