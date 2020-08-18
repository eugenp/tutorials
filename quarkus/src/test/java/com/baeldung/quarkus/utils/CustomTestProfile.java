package com.baeldung.quarkus.utils;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class CustomTestProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Collections.singletonMap("quarkus.resteasy.path", "/custom");
    }

    @Override
    public Set<Class<?>> getEnabledAlternatives() {
        return Collections.singleton(TestBookRepository.class);
    }

    @Override
    public String getConfigProfile() {
        return "custom-profile";
    }

}
