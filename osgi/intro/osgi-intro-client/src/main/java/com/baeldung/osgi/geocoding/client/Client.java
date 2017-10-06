package com.baeldung.osgi.geocoding.client;

import com.baeldung.osgi.service.service.Coord;
import com.baeldung.osgi.service.service.GeocodeException;
import com.baeldung.osgi.service.service.GeocodingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client implements BundleActivator {

    private GeocodingService geocoding;
    private BundleContext context;

    public Client(GeocodingService geocoding) {
        this.geocoding = geocoding;
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
                Coord geocode = geocoding.geocode(address);
                System.out.println("Coordinates:" + geocode);
            } catch (GeocodeException e) {
                System.out.println("It was not possible to geocode the address.");
            }

        }
    }

    public static void main(String[] args) {
        System.out.println("main");
        Client client = null; // new Client(new GeocodeXyz());
        client.run();
    }

    @Override public void start(BundleContext bundleContext) throws Exception {
        System.out.println("start");
        Client client = null; //new Client(new GeocodeXyz());
        client.run();
        this.context = bundleContext;
    }

    @Override public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("stop");
        this.context = bundleContext;
    }
}
