package com.baeldung.causeway.assets.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import org.apache.causeway.core.config.presets.CausewayPresets;

@SpringBootApplication
@Import(AppManifest.class)
public class AssetManagementApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        CausewayPresets.prototyping();
        SpringApplication.run(AssetManagementApplication.class, args);
    }
}
