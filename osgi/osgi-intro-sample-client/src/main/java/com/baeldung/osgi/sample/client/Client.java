package com.baeldung.osgi.sample.client;

import com.baeldung.osgi.sample.service.definition.Greeter;
import org.osgi.framework.*;

public class Client implements BundleActivator, ServiceListener {

    private BundleContext ctx;
    private ServiceReference serviceReference;

    public void start(BundleContext ctx) {
        this.ctx = ctx;
        try {
            ctx.addServiceListener(this, "(objectclass=" + Greeter.class.getName() + ")");
        } catch (InvalidSyntaxException ise) {
            ise.printStackTrace();
        }
    }

    public void stop(BundleContext bundleContext) {
        if (serviceReference != null) {
            ctx.ungetService(serviceReference);
        }
        this.ctx = null;
    }

    public void serviceChanged(ServiceEvent serviceEvent) {
        int type = serviceEvent.getType();
        switch (type) {
        case (ServiceEvent.REGISTERED):
            System.out.println("Notification of service registered.");
            serviceReference = serviceEvent.getServiceReference();
            Greeter service = (Greeter) (ctx.getService(serviceReference));
            System.out.println(service.sayHiTo("John"));
            break;
        case (ServiceEvent.UNREGISTERING):
            System.out.println("Notification of service unregistered.");
            ctx.ungetService(serviceEvent.getServiceReference());
            break;
        default:
            break;
        }
    }
}
