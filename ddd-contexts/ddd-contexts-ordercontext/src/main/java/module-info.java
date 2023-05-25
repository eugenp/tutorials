module com.baeldung.dddcontexts.ordercontext {
    requires com.baeldung.dddcontexts.sharedkernel;
    exports com.baeldung.dddcontexts.ordercontext.service;
    exports com.baeldung.dddcontexts.ordercontext.model;
    exports com.baeldung.dddcontexts.ordercontext.repository;
    provides com.baeldung.dddcontexts.ordercontext.service.OrderService
      with com.baeldung.dddcontexts.ordercontext.service.CustomerOrderService;
}