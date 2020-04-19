module com.baeldung.dddmodules.infrastructure {
    requires transitive com.baeldung.dddmodules.sharedkernel;
    requires transitive com.baeldung.dddmodules.ordercontext;
    requires transitive com.baeldung.dddmodules.shippingcontext;
    provides com.baeldung.dddmodules.sharedkernel.events.EventBus
      with com.baeldung.dddmodules.infrastructure.events.SimpleEventBus;
    provides com.baeldung.dddmodules.ordercontext.repository.CustomerOrderRepository
      with com.baeldung.dddmodules.infrastructure.db.InMemoryOrderStore;
    provides com.baeldung.dddmodules.shippingcontext.repository.ShippingOrderRepository
      with com.baeldung.dddmodules.infrastructure.db.InMemoryOrderStore;
}
