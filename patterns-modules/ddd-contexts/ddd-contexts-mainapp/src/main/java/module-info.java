module com.baeldung.dddcontexts.mainapp {
    uses com.baeldung.dddcontexts.sharedkernel.events.EventBus;
    uses com.baeldung.dddcontexts.ordercontext.service.OrderService;
    uses com.baeldung.dddcontexts.ordercontext.repository.CustomerOrderRepository;
    uses com.baeldung.dddcontexts.shippingcontext.repository.ShippingOrderRepository;
    uses com.baeldung.dddcontexts.shippingcontext.service.ShippingService;
    requires transitive com.baeldung.dddcontexts.infrastructure;
}