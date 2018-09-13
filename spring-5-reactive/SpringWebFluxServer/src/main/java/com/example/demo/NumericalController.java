package com.example.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class NumericalController {
	Logger logger = (Logger) LoggerFactory.getLogger(NumericalController.class);
	int number = 0;

	@RequestMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/generateNumbers")
	public Flux<Integer> getNumber() {

		return Flux.fromStream(Stream.generate(() -> number++).map(s -> Integer.valueOf(s)))
				.delayElements(Duration.ofSeconds(1));
	}
}
