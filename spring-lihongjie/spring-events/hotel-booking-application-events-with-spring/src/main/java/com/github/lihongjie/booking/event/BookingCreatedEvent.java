package com.github.lihongjie.booking.event;

import org.springframework.context.ApplicationEvent;

import com.github.lihongjie.booking.domain.Booking;

public class BookingCreatedEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 2559394707167630502L;

	private Booking booking;
	
	public BookingCreatedEvent(Object source) {
		super(source);
		
	}

	public BookingCreatedEvent(Object source, Booking booking) {
		super(source);
		this.booking = booking;
	}

	public Booking getBooking() {
		return booking;
	}

}
