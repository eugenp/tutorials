module com.baeldung.dddcontexts.shippingcontext {
    requires com.baeldung.dddcontexts.sharedkernel;
    exports com.baeldung.dddcontexts.shippingcontext.service;
    exports com.baeldung.dddcontexts.shippingcontext.model;
    exports com.baeldung.dddcontexts.shippingcontext.repository;
    provides com.baeldung.dddcontexts.shippingcontext.service.ShippingService
      with com.baeldung.dddcontexts.shippingcontext.service.ParcelShippingService;
}
