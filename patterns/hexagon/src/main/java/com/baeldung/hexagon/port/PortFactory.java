package com.baeldung.hexagon.port;

import com.baeldung.hexagon.app.MovieTicketBookingSystem;

public class PortFactory {

	public static MovieTicketBookingPort getPort(String portType) {
		if (portType.equals("Movie")) {
			return new MovieTicketBookingSystem();
		}
		return null;
	}
}
