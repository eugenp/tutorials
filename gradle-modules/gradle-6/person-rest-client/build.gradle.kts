plugins {
    `java-library`
}

sourceCompatibility = "1.8"
targetCompatibility = "1.8"

dependencies {
    api(platform(project(":httpclient-platform")))
    implementation("org.apache.httpcomponents:fluent-hc")
}