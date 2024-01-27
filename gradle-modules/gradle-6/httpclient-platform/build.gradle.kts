plugins {
    `java-platform`
}

sourceCompatibility = "1.8"
targetCompatibility = "1.8"

dependencies {
    constraints {
        api("org.apache.httpcomponents:fluent-hc:4.5.10")
        api("org.apache.httpcomponents:httpclient:4.5.10")
        runtime("commons-logging:commons-logging:1.2")
    }
}