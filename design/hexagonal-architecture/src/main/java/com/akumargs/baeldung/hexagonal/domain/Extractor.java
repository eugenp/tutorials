package com.akumargs.baeldung.hexagonal.domain;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import com.akumargs.baeldung.hexagonal.service.ResourceHandler;

public class Extractor {
    ServiceLocator locator;
    private Map<String, String> extractedProperties;
    private List<String> properties;
    private File file;
    private Map<String, String> replace;

    public Extractor(File file, List<String> properties, Map<String, String> replace) {
        this.file = file;
        this.properties = properties;
        this.locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        this.replace = replace;
        findAndReplace();
    }

    private void findAndReplace() {

        Map<String, String> extractedProps = new HashMap<String, String>();
        // Extract properties by fetching appropriate resource handler
        if (file.getName()
            .endsWith(".json")) {
            ResourceHandler resourceHandler = locator.getService(ResourceHandler.class);
            extractedProps = resourceHandler.extract(this.file, this.properties);
        } else if (file.getName()
            .endsWith(".properties")) {
            ResourceHandler handlerService = locator.getService(ResourceHandler.class);
            extractedProps = handlerService.extract(this.file, this.properties);
        }
        // Replace placeholders
        if (!replace.isEmpty()) {
            extractedProps.entrySet()
                .stream()
                .forEach(entry -> {
                    replace.entrySet()
                        .stream()
                        .forEach(replaceEntry -> {
                            if (entry.getValue()
                                .contains(replaceEntry.getKey())) {
                                entry.setValue(entry.getValue()
                                    .replace(replaceEntry.getKey(), replaceEntry.getValue()));
                            }
                        });
                });
        }
        this.extractedProperties = extractedProps;
    }

    @Override
    public String toString() {
        return this.extractedProperties.toString();
    }
}
