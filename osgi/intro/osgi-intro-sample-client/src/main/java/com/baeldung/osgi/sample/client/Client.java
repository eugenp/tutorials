package com.baeldung.osgi.sample.client;

import com.baeldung.osgi.sample.service.definition.Greeter;
import org.osgi.framework.*;

public class Client implements BundleActivator, ServiceListener {

    private BundleContext ctx;
    private Thread thread;
    private ServiceReference<> serviceReference;

    public void start(BundleContext ctx) {
        //System.out.println("Hello world.");
        this.ctx = ctx;

        try{
            ctx.addServiceListener(this, "(objectclass=" + Greeter.class.getName() + ")");
        }catch (InvalidSyntaxException ise){

        }

    }

    public void stop(BundleContext bundleContext) {

        //System.out.println("Goodbye world.");
        if(serviceReference!=null) {
            ctx.ungetService(serviceEvent.getServiceReference());
        }
        this.ctx = null;
    }

    public void serviceChanged(ServiceEvent serviceEvent) {

        int type = serviceEvent.getType();
        switch (type){
            case(ServiceEvent.UNREGISTERING):
                ctx.ungetService(serviceEvent.getServiceReference());
                break;
            case(ServiceEvent.REGISTERED):
                serviceReference = serviceEvent.getServiceReference();
                Greeter service;
                service = ctx.getService(serviceReference);
                System.out.println( service.sayHiTo("John") );
                break;
            default:
                break;
        }
    }
}
