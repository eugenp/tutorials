module com.baeldung.dddmodules.shippingcontext {
    requires com.baeldung.dddmodules.sharedkernel;
    exports com.baeldung.dddmodules.shippingcontext.service;
    exports com.baeldung.dddmodules.shippingcontext.model;
    exports com.baeldung.dddmodules.shippingcontext.repository;
    provides com.baeldung.dddmodules.shippingcontext.service.ShippingService
      with com.baeldung.dddmodules.shippingcontext.service.ParcelShippingService;
}
