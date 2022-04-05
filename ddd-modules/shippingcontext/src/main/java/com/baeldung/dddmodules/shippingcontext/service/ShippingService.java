package com.baeldung.dddmodules.shippingcontext.service;

import com.baeldung.dddmodules.sharedkernel.service.ApplicationService;
import com.baeldung.dddmodules.shippingcontext.model.Parcel;
import com.baeldung.dddmodules.shippingcontext.repository.ShippingOrderRepository;

import java.util.Optional;

public interface ShippingService extends ApplicationService {
    void shipOrder(int orderId);

    void listenToOrderEvents();

    Optional<Parcel> getParcelByOrderId(int orderId);

    void setOrderRepository(ShippingOrderRepository orderRepository);
}
