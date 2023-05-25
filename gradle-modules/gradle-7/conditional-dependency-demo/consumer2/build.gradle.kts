plugins {
    id("java")
}

group = "com.baeldung.gradle"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        if (project.hasProperty("isLocal"))
            substitute(project(":provider1"))
                    .using(project(":provider2"))
                    .because("Project property override(isLocal).")
    }
}

dependencies {
    implementation(project(":provider1"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
