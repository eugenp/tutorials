package com.baeldung.flogger;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Test;

import com.google.common.flogger.FluentLogger;

public class FloggerManualTest {
	static {
//        System.setProperty("flogger.backend_factory", "com.google.common.flogger.backend.log4j.Log4jBackendFactory#getInstance");
		System.setProperty("flogger.backend_factory",
				"com.google.common.flogger.backend.slf4j.Slf4jBackendFactory#getInstance");
	}
	private static final FluentLogger logger = FluentLogger.forEnclosingClass();

	@Test
	public void givenATimeInterval_shouldLogAfterEveryTimeInterval() {
		IntStream.range(0, 1_000_0000).forEach(value -> {
			logger.atInfo().atMostEvery(10, TimeUnit.SECONDS).log("This log shows [every 10 seconds] => %d", value);
		});
	}

}
