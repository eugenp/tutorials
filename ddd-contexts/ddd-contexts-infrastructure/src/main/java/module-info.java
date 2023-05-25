import com.baeldung.dddcontexts.infrastructure.db.InMemoryOrderStore;
import com.baeldung.dddcontexts.infrastructure.events.SimpleEventBus;

module com.baeldung.dddcontexts.infrastructure {
    requires transitive com.baeldung.dddcontexts.sharedkernel;
    requires transitive com.baeldung.dddcontexts.ordercontext;
    requires transitive com.baeldung.dddcontexts.shippingcontext;
    provides com.baeldung.dddcontexts.sharedkernel.events.EventBus
      with SimpleEventBus;
    provides com.baeldung.dddcontexts.ordercontext.repository.CustomerOrderRepository
      with InMemoryOrderStore;
    provides com.baeldung.dddcontexts.shippingcontext.repository.ShippingOrderRepository
      with InMemoryOrderStore;
}
