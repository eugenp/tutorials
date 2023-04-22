package com.baeldung.version;

import org.springframework.boot.system.JavaVersion;
import org.springframework.boot.system.SystemProperties;
import org.springframework.core.SpringVersion;

public class VersionObtainer {

    public String getSpringVersion() {
        return SpringVersion.getVersion();
    }
    
    public String getJavaVersion() {
        return JavaVersion.getJavaVersion().toString();
    }
    
    public String getJdkVersion() {
        return SystemProperties.get("java.version");
    }
}
