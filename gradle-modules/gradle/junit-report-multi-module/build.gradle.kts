plugins {
	id("java")
	id("jvm-test-suite")
	id("test-report-aggregation")
}

group = "com.baeldung.gradle"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

testing {
	suites {
		val test by getting(JvmTestSuite::class) {
			useJUnitJupiter()
		}
	}
}

reporting {
	reports {
		val testAggregateTestReport by existing(AggregateTestReport::class)
	}
}

dependencies {
	subprojects.forEach { sub ->
		testReportAggregation(project(sub.path))
	}
}