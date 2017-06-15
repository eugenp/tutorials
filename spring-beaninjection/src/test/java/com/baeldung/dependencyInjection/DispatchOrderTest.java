package com.baeldung.dependencyInjection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.dependencyInjection.domain.CreditCard;
import com.baeldung.dependencyInjection.domain.CreditCardImpl;
import com.baeldung.dependencyInjection.domain.DeliverySlot;
import com.baeldung.dependencyInjection.domain.DeliverySlotImpl;
import com.baeldung.dependencyInjection.domain.DispatchOrder;

public class DispatchOrderTest {

    @Test
    public void IfDeliverySlotIsSelected_thenReturnValueTrue() {
        DeliverySlot dSlot = new DeliverySlotImpl();
        CreditCard cc = new CreditCardImpl();
        DispatchOrder dispOrder = new DispatchOrder(cc);
        dispOrder.setDeliverySlot(dSlot);

        assertEquals(true, dispOrder.isDeliverySlotSelected());
    }

    @Test
    public void IfOrderIsDispatched_thenReturnValueTrue() {
        CreditCard cc = new CreditCardImpl();
        DispatchOrder dispOrder = new DispatchOrder(cc);

        assertEquals(true, dispOrder.processOrderDispatch());
    }
    
    @Test (expected = IllegalStateException.class)
    public void IfCreditCardInstanceNull_thenExceptionThrown() {
        DispatchOrder dispOrder = new DispatchOrder(null);

        assertEquals(true, dispOrder.processOrderDispatch());
    }

}
