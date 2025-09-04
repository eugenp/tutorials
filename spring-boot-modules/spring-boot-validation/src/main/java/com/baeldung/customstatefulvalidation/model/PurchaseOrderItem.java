package com.baeldung.customstatefulvalidation.model;

import com.baeldung.customstatefulvalidation.validators.AvailableChannel;
import com.baeldung.customstatefulvalidation.validators.AvailableWarehouseRoute;
import com.baeldung.customstatefulvalidation.validators.ChoosePacksOrIndividuals;
import com.baeldung.customstatefulvalidation.validators.ProductCheckDigit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@ChoosePacksOrIndividuals
@AvailableWarehouseRoute
public class PurchaseOrderItem {

    @ProductCheckDigit
    @NotNull
    @Pattern(regexp = "A-\\d{8}-\\d")
    private String productId;

    private String sourceWarehouse;
    private String destinationCountry;

    @AvailableChannel
    private String tenantChannel;

    private int numberOfIndividuals;
    private int numberOfPacks;
    private int itemsPerPack;

    @org.hibernate.validator.constraints.UUID
    private String clientUuid;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSourceWarehouse() {
        return sourceWarehouse;
    }

    public void setSourceWarehouse(String sourceWarehouse) {
        this.sourceWarehouse = sourceWarehouse;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getTenantChannel() {
        return tenantChannel;
    }

    public void setTenantChannel(String tenantChannel) {
        this.tenantChannel = tenantChannel;
    }

    public int getNumberOfIndividuals() {
        return numberOfIndividuals;
    }

    public void setNumberOfIndividuals(int numberOfIndividuals) {
        this.numberOfIndividuals = numberOfIndividuals;
    }

    public int getNumberOfPacks() {
        return numberOfPacks;
    }

    public void setNumberOfPacks(int numberOfPacks) {
        this.numberOfPacks = numberOfPacks;
    }

    public int getItemsPerPack() {
        return itemsPerPack;
    }

    public void setItemsPerPack(int itemsPerPack) {
        this.itemsPerPack = itemsPerPack;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }
}
