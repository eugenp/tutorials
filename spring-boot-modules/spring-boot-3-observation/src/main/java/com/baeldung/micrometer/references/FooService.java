package com.baeldung.micrometer.references;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
@RequestMapping("/micrometer/references")
class FooService {

	private static final Logger logger = LoggerFactory.getLogger(FooService.class);
	private final MeterRegistry registry;

	Foo fooField = new Foo(10);


	public FooService(MeterRegistry registry) {
		this.registry = registry;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void setupGauges() {
		setupWeakReferenceGauge();
		setupStrongReferenceGauge();
		setupFieldGauge();
	}

	// todo: rename
	private void setupFieldGauge() {
		Gauge.builder("foo.value.field", fooField, Foo::value)
				.description("Foo value - weak reference (will show NaN after GC)")
				.register(registry);

		logger.info("Created weak reference gauge with value: {}", fooField.value());
	}

	private void setupWeakReferenceGauge() {
		Foo foo = new Foo(10);

		Gauge.builder("foo.value.weak", foo, Foo::value)
				.description("Foo value - weak reference (will show NaN after GC)")
				.register(registry);

		logger.info("Created weak reference gauge with value: {}", foo.value());
	}

	private void setupStrongReferenceGauge() {
		Foo foo = new Foo(20);

		Gauge.builder("foo.value.strong", foo, Foo::value)
				.description("Foo value - strong reference (will persist)")
				.strongReference(true).register(registry);

		logger.info("Created strong reference gauge with value: {}",
				foo.value());
	}

	@GetMapping("/gc")
	public String triggerGC() {
		logger.info("Suggesting garbage collection...");
		System.gc();
		logger.info("Garbage collection suggested");
		return "Garbage collection triggered. Check metrics after a few seconds.";
	}

	record Foo(int value) {
	}

}