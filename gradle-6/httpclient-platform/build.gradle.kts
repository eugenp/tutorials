plugins {
    `java-platform`
}

dependencies {
    constraints {
        api("org.apache.httpcomponents:fluent-hc:4.5.10")
        api("org.apache.httpcomponents:httpclient:4.5.10")
        runtime("commons-logging:commons-logging:1.2")
    }
}