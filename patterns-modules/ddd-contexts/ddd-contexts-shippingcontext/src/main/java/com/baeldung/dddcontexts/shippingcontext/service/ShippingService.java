package com.baeldung.dddcontexts.shippingcontext.service;

import com.baeldung.dddcontexts.sharedkernel.service.ApplicationService;
import com.baeldung.dddcontexts.shippingcontext.model.Parcel;
import com.baeldung.dddcontexts.shippingcontext.repository.ShippingOrderRepository;

import java.util.Optional;

public interface ShippingService extends ApplicationService {
    void shipOrder(int orderId);

    void listenToOrderEvents();

    Optional<Parcel> getParcelByOrderId(int orderId);

    void setOrderRepository(ShippingOrderRepository orderRepository);
}
