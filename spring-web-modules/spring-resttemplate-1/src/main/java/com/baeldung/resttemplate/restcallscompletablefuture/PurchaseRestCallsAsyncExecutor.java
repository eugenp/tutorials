package com.baeldung.resttemplate.restcallscompletablefuture;

import static java.lang.String.format;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class PurchaseRestCallsAsyncExecutor {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://internal-api.com";

    public PurchaseRestCallsAsyncExecutor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void updatePurchases(List<Purchase> purchases) {
        purchases.forEach(this::updatePurchase);
    }

    public void updatePurchasesHandlingExceptions(List<Purchase> purchases) {
        purchases.forEach(this::updatePurchaseHandlingExceptions);
    }

    public void updatePurchase(Purchase purchase) {
        CompletableFuture.allOf(CompletableFuture.supplyAsync(() -> getOrderDescription(purchase.getOrderId()))
                    .thenAccept(purchase::setOrderDescription),
                CompletableFuture.supplyAsync(() -> getPaymentDescription(purchase.getPaymentId()))
                    .thenAccept(purchase::setPaymentDescription),
                CompletableFuture.supplyAsync(() -> getUserName(purchase.getUserId()))
                    .thenAccept(purchase::setBuyerName))
            .join();
    }

    public void updatePurchaseHandlingExceptions(Purchase purchase) {
        CompletableFuture.allOf(CompletableFuture.supplyAsync(() -> getOrderDescription(purchase.getOrderId()))
                    .thenAccept(purchase::setOrderDescription)
                    .orTimeout(1, TimeUnit.SECONDS)
                    .handle(handleGracefully()),
                CompletableFuture.supplyAsync(() -> getPaymentDescription(purchase.getPaymentId()))
                    .thenAccept(purchase::setPaymentDescription)
                    .orTimeout(1, TimeUnit.SECONDS)
                    .handle(handleGracefully()),
                CompletableFuture.supplyAsync(() -> getUserName(purchase.getUserId()))
                    .thenAccept(purchase::setBuyerName)
                    .orTimeout(1, TimeUnit.SECONDS)
                    .handle(handleGracefully()))
            .join();
    }

    public String getOrderDescription(String orderId) {
        ResponseEntity<String> result = restTemplate.getForEntity(format("%s/orders/%s", BASE_URL, orderId), String.class);

        return result.getBody();
    }

    public String getPaymentDescription(String paymentId) {
        ResponseEntity<String> result = restTemplate.getForEntity(format("%s/payments/%s", BASE_URL, paymentId), String.class);

        return result.getBody();
    }

    public String getUserName(String userId) {
        ResponseEntity<String> result = restTemplate.getForEntity(format("%s/users/%s", BASE_URL, userId), String.class);

        return result.getBody();
    }

    private static BiFunction<Void, Throwable, Void> handleGracefully() {
        return (result, exception) -> {
            if (exception != null) {
                // handle exception
                return null;
            }
            return result;
        };
    }
}
