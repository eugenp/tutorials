package com.baeldung.maven_caching;

import com.google.common.io.Files;

public class CoreClass {

    public String method() {
        return "Hello from core module!!";
    }

    public String dependencyMethod() {
        return Files.simplifyPath("/home/app/test");
    }
}
