package com.baeldung.simpleopenai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;
import java.util.Objects;

import io.github.sashirestela.openai.common.function.FunctionDef;
import io.github.sashirestela.openai.common.function.FunctionExecutor;
import io.github.sashirestela.openai.common.function.Functional;

public final class HotelFunctions {

    private static HotelService HOTEL_SERVICE;

    private HotelFunctions() {
    }

    public static FunctionExecutor createFunctionExecutor(HotelService hotelService) {
        HOTEL_SERVICE = Objects.requireNonNull(hotelService, "hotelService");

        FunctionExecutor executor = new FunctionExecutor();

        executor.enrollFunction(FunctionDef.builder()
            .name("search_hotels")
            .description("Search for available hotels given a city, check-in date, nights, and guests")
            .functionalClass(SearchHotels.class)
            .strict(Boolean.TRUE)
            .build());

        executor.enrollFunction(FunctionDef.builder()
            .name("create_booking")
            .description("Create a booking given a hotel id, check-in date, nights, guests, and guest name")
            .functionalClass(CreateBooking.class)
            .strict(Boolean.TRUE)
            .build());

        return executor;
    }

    public record SearchHotelsResult(List<HotelService.HotelOffer> offers) {
    }

    public static class SearchHotels implements Functional {

        @JsonPropertyDescription("City name, for example: Tokyo")
        @JsonProperty(required = true)
        public String city;

        @JsonPropertyDescription("Check-in date in ISO-8601 format, for example: 2026-01-10")
        @JsonProperty(required = true)
        public String checkIn;

        @JsonPropertyDescription("Number of nights to stay")
        @JsonProperty(required = true)
        public int nights;

        @JsonPropertyDescription("Number of guests")
        @JsonProperty(required = true)
        public int guests;

        @Override
        public Object execute() {
            List<HotelService.HotelOffer> offers =
                HOTEL_SERVICE.searchOffers(city, checkIn, nights, guests);

            return new SearchHotelsResult(offers);
        }
    }

    public static class CreateBooking implements Functional {

        @JsonPropertyDescription("Hotel identifier returned by search_hotels")
        @JsonProperty(required = true)
        public String hotelId;

        @JsonPropertyDescription("Check-in date in ISO-8601 format, for example: 2026-01-10")
        @JsonProperty(required = true)
        public String checkIn;

        @JsonPropertyDescription("Number of nights to stay")
        @JsonProperty(required = true)
        public int nights;

        @JsonPropertyDescription("Number of guests")
        @JsonProperty(required = true)
        public int guests;

        @JsonPropertyDescription("Guest full name for the booking")
        @JsonProperty(required = true)
        public String guestName;

        @Override
        public Object execute() {
            return HOTEL_SERVICE.createBooking(hotelId, checkIn, nights, guests, guestName);
        }
    }
}
