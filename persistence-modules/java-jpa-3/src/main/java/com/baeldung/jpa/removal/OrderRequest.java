package com.baeldung.jpa.removal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

@Entity
public class OrderRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
    private ShipmentInfo shipmentInfo;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "orderRequest")
    private List<LineItem> lineItems;

    public OrderRequest(ShipmentInfo shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
    }

    public OrderRequest(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void removeLineItem(LineItem lineItem) {
        lineItems.remove(lineItem);
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    protected OrderRequest() {
    }
}
