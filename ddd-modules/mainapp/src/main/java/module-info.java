module com.baeldung.dddmodules.mainapp {
    uses com.baeldung.dddmodules.sharedkernel.events.EventBus;
    uses com.baeldung.dddmodules.ordercontext.service.OrderService;
    uses com.baeldung.dddmodules.ordercontext.repository.CustomerOrderRepository;
    uses com.baeldung.dddmodules.shippingcontext.repository.ShippingOrderRepository;
    uses com.baeldung.dddmodules.shippingcontext.service.ShippingService;
    requires transitive com.baeldung.dddmodules.infrastructure;
}