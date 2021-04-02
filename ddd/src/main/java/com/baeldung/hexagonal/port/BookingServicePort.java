package com.baeldung.hexagonal.port;

import java.util.Set;

public interface BookingServicePort {

    BookingResponse book(BookingRequest request);

    class BookingRequest {
        private String movieShowId;
        private String customerId;
        private String theatreId;
        private Set<String> seats;
        private Double amount;

        public String getMovieShowId() {
            return movieShowId;
        }

        public void setMovieShowId(String movieShowId) {
            this.movieShowId = movieShowId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getTheatreId() {
            return theatreId;
        }

        public void setTheatreId(String theatreId) {
            this.theatreId = theatreId;
        }

        public Set<String> getSeats() {
            return seats;
        }

        public void setSeats(Set<String> seats) {
            this.seats = seats;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    class BookingResponse {
        public static final int SUCCESS = 0;
        public static final int SEAT_NOT_AVAILABLE = 1;
        public static final int PAYMENT_FAILED = 2;
        public static final int UNKNOWN_ERROR = 3;

        private final int statusCode;

        public BookingResponse(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}
