package com.baeldung.restcallscompletablefuture;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import static java.lang.String.format;

@Component
public class UpdatePurchaseData {

    private final RestTemplate restTemplate;

    public UpdatePurchaseData(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void updatePurchases(List<Purchase> purchases) {
        purchases.forEach(this::updatePurchase);
    }

    public void updatePurchase(Purchase purchase) {
        CompletableFuture.allOf(
                CompletableFuture.
                        supplyAsync(() -> getOrderDescription(purchase.getOrderId()))
                        .thenAccept(purchase::setOrderDescription),
                CompletableFuture.
                        supplyAsync(() -> getPaymentDescription(purchase.getPaymentId()))
                        .thenAccept(purchase::setPaymentDescription),
                CompletableFuture.
                        supplyAsync(() -> getUserName(purchase.getUserId()))
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
        ResponseEntity<String> result = restTemplate.getForEntity(format("/orders/%s", orderId),
                String.class);

        return result.getBody();
    }

    public String getPaymentDescription(String paymentId) {
        ResponseEntity<String> result = restTemplate.getForEntity(format("/payments/%s", paymentId),
                String.class);

        return result.getBody();
    }

    public String getUserName(String userId) {
        ResponseEntity<String> result = restTemplate.getForEntity(format("/users/%s", userId),
                String.class);

        return result.getBody();
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
