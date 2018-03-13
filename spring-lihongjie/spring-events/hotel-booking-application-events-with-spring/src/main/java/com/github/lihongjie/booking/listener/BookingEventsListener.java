package com.github.lihongjie.booking.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.github.lihongjie.booking.event.BookingCreatedEvent;

@Component
public class BookingEventsListener implements ApplicationListener<BookingCreatedEvent> {

//	private static final Logger log = Logger.getLogger(BookingEventsListener.class);

	@Override
	public void onApplicationEvent(BookingCreatedEvent event) {
	
		
		System.out.println("id is " + event.getBooking().getId());
	}

}
