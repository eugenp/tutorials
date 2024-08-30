package com.baeldung.cloud.openfeign.completablefuturefeignclient;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PurchaseService {

    private final PaymentMethodClient paymentMethodClient;
    private final ReportClient reportClient;

    public PurchaseService(PaymentMethodClient paymentMethodClient, ReportClient reportClient) {
        this.paymentMethodClient = paymentMethodClient;
        this.reportClient = reportClient;
    }

    public void executePurchase(Purchase purchase) {

        ReportRequest report = new ReportRequest("Creating purchase for order", purchase.getOrderId(), purchase.getSiteId());

        CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> paymentMethodClient.getAvailablePaymentMethods(purchase.getSiteId())),
                CompletableFuture.runAsync(() -> reportClient.sendReport(report))
        );
    }
}
