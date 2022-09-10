plugins {
    id("java")
}

group = "com.baeldung.gradle"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.7.0")

    if(project.hasProperty("isLocal")) {
        implementation("com.baeldung.gradle:provider1")
    } else {
        implementation("com.baeldung.gradle:provider2")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
