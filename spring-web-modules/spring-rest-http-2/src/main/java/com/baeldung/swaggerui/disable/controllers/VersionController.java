package com.baeldung.swaggerui.disable.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private final Environment environment;

    public VersionController(Environment environment) {
        this.environment = environment;
    }

    @ApiOperation(value = "Get the currently deployed API version and active Spring profiles")
    @GetMapping("/api/version")
    public Version getVersion() {
        return new Version("1.0", environment.getActiveProfiles());
    }

    private static class Version {
        private final String version;
        private String[] activeProfiles;

        private Version(String version, String[] activeProfiles) {
            this.version = version;
            this.activeProfiles = activeProfiles;
        }

        public String getVersion() {
            return version;
        }

        public String[] getActiveProfiles() {
            return activeProfiles;
        }
    }
}
