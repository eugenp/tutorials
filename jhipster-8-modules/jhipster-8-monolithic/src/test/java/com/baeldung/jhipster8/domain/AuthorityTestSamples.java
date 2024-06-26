package com.baeldung.jhipster8.domain;

import java.util.UUID;

public class AuthorityTestSamples {

    public static Authority getAuthoritySample1() {
        return new Authority().name("name1");
    }

    public static Authority getAuthoritySample2() {
        return new Authority().name("name2");
    }

    public static Authority getAuthorityRandomSampleGenerator() {
        return new Authority().name(UUID.randomUUID().toString());
    }
}
