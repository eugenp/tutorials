plugins {
    `java-library`
    `java-test-fixtures`
}

dependencies {
    testFixturesApi("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testFixturesImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.test {
    useJUnitPlatform()
}