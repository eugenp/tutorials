package com.akumargs.baeldung.hexagonal;

import java.io.File;
import java.util.*;

import org.glassfish.hk2.api.*;
import org.glassfish.hk2.utilities.DescriptorImpl;

import com.akumargs.baeldung.hexagonal.domain.Extractor;

public class Main {

    public static void main(String[] args) {
        if (!new File("src/main/resources/sample.json").exists()) {
            System.out.println("File not found");
            System.exit(1);
        }
        init();
        List<String> properties = Arrays.asList("city", "population", "language", "current_time");
        Map<String, String> replace = new HashMap<String, String>();
        replace.put("$time", new Date().toGMTString());
        System.out.println("Extracted properties");
        System.out.println(new Extractor(new File("src/main/resources/sample.json"), properties, replace));
    }

    private static void init() {
        ServiceLocatorFactory factory = ServiceLocatorFactory.getInstance();

        ServiceLocator locator = factory.create("ExtractorServiceLocator");

        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);

        DynamicConfiguration config = dcs.createDynamicConfiguration();

        config.bind(createExtractorDescriptor());

        config.commit();
    }

    private static Descriptor createExtractorDescriptor() {
        DescriptorImpl retVal = new DescriptorImpl();

        retVal.setImplementation("com.akumargs.baeldung.hexagonal.serviceImpl.JsonHandler");
        retVal.addAdvertisedContract("com.akumargs.baeldung.hexagonal.serviceImpl.JsonHandler");
        retVal.addAdvertisedContract("com.akumargs.baeldung.hexagonal.service.ResourceHandler");
        retVal.setScope("org.glassfish.api.PerLookup");

        return retVal;
    }

}
