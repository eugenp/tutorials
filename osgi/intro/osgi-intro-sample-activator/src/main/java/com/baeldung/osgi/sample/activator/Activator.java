package com.baeldung.osgi.sample.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext bundleContext) {
        System.out.println("start() invoked.");
    }

    public void stop(BundleContext bundleContext) {
        System.out.println("stop() invoked.");
    }

}