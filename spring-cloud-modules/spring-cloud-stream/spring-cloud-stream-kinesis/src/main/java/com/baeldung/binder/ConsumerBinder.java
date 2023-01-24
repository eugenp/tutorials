package com.baeldung.binder;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class ConsumerBinder {

	@StreamListener(Sink.INPUT)
	public void consume(String ip) {
		System.out.println(ip);
	}
}