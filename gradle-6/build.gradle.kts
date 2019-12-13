plugins {
    `java-library`
    `maven-publish`
}

group = "com.baeldung"
version = "1.0.0"

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}