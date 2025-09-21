package com.baeldung.customstatefulvalidation.model;

import java.util.UUID;

public class PurchaseOrderItemFactory {

    public static PurchaseOrderItem createValidPurchaseOrderItem() {
        PurchaseOrderItem item = new PurchaseOrderItem();

        item.setProductId("A-12345678-6");
        item.setClientUuid(UUID.randomUUID().toString());
        item.setNumberOfIndividuals(12);
        item.setTenantChannel("retail");
        item.setSourceWarehouse("Springfield");
        item.setDestinationCountry("USA");

        return item;
    }
}
