plugins {
    id("java")
}

description = "gradle-source-vs-target-compatibility"

group = "com.baeldung"

tasks.compileJava {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}