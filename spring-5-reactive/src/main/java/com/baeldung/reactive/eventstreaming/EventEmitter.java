package com.baeldung.reactive.eventstreaming;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

@Component
public class EventEmitter {

	public Flux<Event> emitEvent() {
		return Flux.<Event>create(fluxSink -> {
			int index = 1;
			while (!fluxSink.isCancelled()) {
				Event event = new Event("Hello: " + index++);
				System.out.println(event.toString());
				fluxSink.next(event);
				sleepOneSecond();
			}
		})
			.share();
	}

	private void sleepOneSecond() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
