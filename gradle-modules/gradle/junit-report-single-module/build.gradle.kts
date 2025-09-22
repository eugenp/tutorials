plugins {
	id("java")
	id("jacoco")
}

group = "com.baeldung.gradle"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
	useJUnitPlatform()

	reports {
		html.required = true
		junitXml.required = true
	}

	finalizedBy(tasks.jacocoTestReport)
}