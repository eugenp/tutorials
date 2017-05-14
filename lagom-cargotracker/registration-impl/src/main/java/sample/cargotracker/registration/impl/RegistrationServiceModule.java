package sample.cargotracker.registration.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import sample.cargotracker.registration.api.RegistrationService;

/**
 * The module that binds the RegistrationService so that it can be served.
 */
public class RegistrationServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
      bindServices(serviceBinding(RegistrationService.class, RegistrationServiceImpl.class));
    }
}
