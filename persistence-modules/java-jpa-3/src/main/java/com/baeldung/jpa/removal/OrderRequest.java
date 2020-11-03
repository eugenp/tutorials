package com.baeldung.jpa.removal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class OrderRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    protected OrderRequest() {
    }
}
