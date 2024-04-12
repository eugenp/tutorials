plugins {
    java
    `maven-publish`
}

sourceCompatibility = "1.8"
targetCompatibility = "1.8"

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}