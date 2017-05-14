package sample.cargotracker.shipping.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import sample.cargotracker.registration.api.RegistrationService;
import sample.cargotracker.shipping.api.ShippingService;

/**
 * The module that binds the ShippingService so that it can be served.
 */
public class ShippingServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindServices(serviceBinding(ShippingService.class, ShippingServiceImpl.class));
        bindClient(RegistrationService.class);
    }
}
