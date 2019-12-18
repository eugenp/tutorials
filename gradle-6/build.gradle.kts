plugins {
    `java-library`
    `maven-publish`
}

group = "com.baeldung"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    api("io.reactivex.rxjava2:rxjava:2.2.16")
    implementation("com.google.guava:guava") {
        version {
            require("2.0")
            prefer("28.1-jre")
            because("Only uses ImmutableList type, so any version since 2.0 will do, but tested with 28.1-jre")
        }
    }

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.compileJava {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}