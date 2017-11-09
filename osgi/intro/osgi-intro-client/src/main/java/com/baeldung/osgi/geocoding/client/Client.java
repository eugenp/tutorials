package com.baeldung.osgi.geocoding.client;

import com.baeldung.osgi.service.Coord;
import com.baeldung.osgi.service.GeocodeException;
import com.baeldung.osgi.service.GeocodingService;
import org.osgi.framework.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.osgi.framework.ServiceEvent.*;

public class Client implements BundleActivator, ServiceListener {
    private BundleContext context;

    private GeocodingService geocodingService;

    public Client(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    public Client() {
        this.geocodingService = geocodingService;
    }

    public static void main(String[] args) {
        System.out.println("main");
        Client client = null; // new Client(new GeocodeXyz());
        client.run();
    }

    private void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String address = null;
        while (true) {
            try {
                System.out.print("Insert an address to geocode:");
                address = in.readLine();
            } catch (IOException e) {
                // should really not happen
            }

            try {
                Coord geocode = geocodingService.geocode(address);
                System.out.println("Coordinates:" + geocode);
            } catch (GeocodeException e) {
                System.out.println("It was not possible to geocode the address.");
            }

        }
    }

    @Override public void start(BundleContext bundleContext) throws Exception {
        System.out.println("starting client bundle");

        bundleContext.addServiceListener(this);
        this.context = bundleContext;
        ServiceReference<GeocodingService> serviceReference = bundleContext.getServiceReference(GeocodingService.class);
        if (serviceReference != null) {
            geocodingService = bundleContext.getService(serviceReference);
        }

        run();
    }

    @Override public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("starting client bundle");
        bundleContext.removeServiceListener(this);
    }

    @Override public void serviceChanged(ServiceEvent serviceEvent) {

        if (!serviceEvent.getServiceReference().getClass().equals(GeocodingService.class)) {
            System.out.println("Ingoring event on service " + serviceEvent.getServiceReference().getClass());
        }

        switch (serviceEvent.getType()) {
        case REGISTERED:
            break;
        case MODIFIED:
            break;
        case MODIFIED_ENDMATCH:
            break;
        case UNREGISTERING:
            break;
        default:
            throw new IllegalStateException("Unsupported type");
        }
    }
}
