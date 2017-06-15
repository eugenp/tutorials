package com.baeldung.dependencyInjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DispatchOrder {

    private CreditCard cc;
    private DeliverySlot dSlot;
    private boolean isOrderDispatched;
    private boolean isDeliverySlotSelected;

    @Autowired
    public DispatchOrder(CreditCard cc) {
        this.cc = cc;
    }

    @Autowired
    public void setDeliverySlot(DeliverySlot dSlot) {
        this.dSlot = dSlot;
    }

    public boolean isDeliverySlotSelected() {
        if (dSlot != null) {
            if (dSlot.deliveryTimeSlotSelected()) {
                isDeliverySlotSelected = true;
            }
        }
        return isDeliverySlotSelected;
    }

    public boolean processOrderDispatch() {
        if (cc != null) {
            if (cc.validateCreditCard()) {
                isOrderDispatched = true;
            }
        } else {
            throw new IllegalStateException("CreditCard instance cannot be null");
        }
        return isOrderDispatched;
    }
}
