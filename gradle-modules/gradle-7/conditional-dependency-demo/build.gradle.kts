plugins {
	id("org.springframework.boot") version "2.7.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("java")
}

group = "com.baeldung.gradle"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter:2.7.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.2")
}

tasks.getByName<Test>("test") {
	useJUnitPlatform()
}
