package com.baeldung.jersey.server.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

public class ViewApplicationConfig extends ResourceConfig {
    
    public ViewApplicationConfig() {
        packages("com.baeldung.jersey.server");
        property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "templates/freemarker");
        register(FreemarkerMvcFeature.class);;
    }

}
