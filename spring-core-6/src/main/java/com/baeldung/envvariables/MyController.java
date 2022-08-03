package com.baeldung.envvariables.valueinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Value("${environment.name}")
    private String environmentName;

    @Value("${java.home.and.environment}")
    private String javaHomeAndEnvironmentName;

    @Value("${thispropertydoesnotexist}")
    private String nonExistentProperty;

    @Value("${baeldung.presentation}")
    private String baeldungPresentation;

    @Autowired
    private Environment environment;

    @Autowired
    private BaeldungProperties baeldungProperties;

    @GetMapping("/environment_name")
    String getEnvironmentName_FromEnvironmentVariables() {
        return environmentName;
    }

    @GetMapping("/java_home_and_environment")
    String getJavaHomeAndEnvironmentName_FromEnvironmentVariables() {
        return javaHomeAndEnvironmentName;
    }

    @GetMapping("non_existent_property")
    String getNonexistentProperty_FromEnvironmentVariables() {
        return nonExistentProperty;
    }

    @GetMapping("baeldung_presentation_from_value")
    String getBaeldungPresentation_FromValue() {
        return baeldungPresentation;
    }

    @GetMapping("baeldung_presentation_from_environment")
    String getBaeldungPresentation_FromEnvironment() {
        return environment.getProperty("baeldung.presentation");
    }

    @GetMapping("baeldung_configuration_properties")
    String getBaeldungPresentation_FromConfigurationProperties() {
        return baeldungProperties.getPresentation();
    }

}
