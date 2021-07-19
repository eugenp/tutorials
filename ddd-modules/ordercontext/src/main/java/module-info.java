module com.baeldung.dddmodules.ordercontext {
    requires com.baeldung.dddmodules.sharedkernel;
    exports com.baeldung.dddmodules.ordercontext.service;
    exports com.baeldung.dddmodules.ordercontext.model;
    exports com.baeldung.dddmodules.ordercontext.repository;
    provides com.baeldung.dddmodules.ordercontext.service.OrderService
      with com.baeldung.dddmodules.ordercontext.service.CustomerOrderService;
}