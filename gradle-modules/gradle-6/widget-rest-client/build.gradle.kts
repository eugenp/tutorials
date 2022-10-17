plugins {
    `java-library`
}

dependencies {
    api(platform(project(":httpclient-platform")))
    implementation("org.apache.httpcomponents:httpclient")
}