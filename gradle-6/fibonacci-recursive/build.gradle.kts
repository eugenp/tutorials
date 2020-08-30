plugins {
    `java-library`
}

dependencies {
    api(project(":fibonacci-spi"))
    compileOnly("com.google.auto.service:auto-service-annotations:1.0-rc6")
    annotationProcessor("com.google.auto.service:auto-service:1.0-rc6")

    testImplementation(testFixtures(project(":fibonacci-spi")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.test {
    useJUnitPlatform()
}