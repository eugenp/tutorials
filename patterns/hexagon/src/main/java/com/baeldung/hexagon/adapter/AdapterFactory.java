package com.baeldung.hexagon.adapter;

import com.baeldung.hexagon.app.MovieTicketBookingSystem;
import com.baeldung.hexagon.port.MovieTicketBookingPort;

public class AdapterFactory {

    public static MovieTicketBookingPort getAdapter(String type) {
        if (type.equals("Movie")) {
            return new MovieTicketBookingAdapter(new MovieTicketBookingSystem());
        }
        return null;
    }
}
