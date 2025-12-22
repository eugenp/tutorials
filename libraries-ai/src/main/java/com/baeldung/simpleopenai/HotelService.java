package com.baeldung.simpleopenai;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class HotelService {

    private final List<Hotel> inventory;

    public HotelService() {
        this.inventory = new ArrayList<>(List.of(
            new Hotel("HTL-001", "Sakura Central Hotel", "Tokyo", 170, 2),
            new Hotel("HTL-002", "Asakusa Riverside Inn", "Tokyo", 130, 3),
            new Hotel("HTL-003", "Shinjuku Business Stay", "Tokyo", 110, 2),
            new Hotel("HTL-004", "Gion Garden Hotel", "Kyoto", 160, 2),
            new Hotel("HTL-005", "Kyoto Station Plaza", "Kyoto", 120, 3),
            new Hotel("HTL-006", "Dotonbori Lights Hotel", "Osaka", 140, 2)
        ));
    }

    public List<HotelOffer> searchOffers(String city, String checkIn, int nights, int guests) {
        Objects.requireNonNull(city, "city");
        Objects.requireNonNull(checkIn, "checkIn");

        LocalDate.parse(checkIn);

        return inventory.stream()
            .filter(h -> h.city().equalsIgnoreCase(city))
            .filter(h -> h.maxGuests() >= guests)
            .map(h -> toOffer(h, nights, guests))
            .sorted(Comparator.comparingInt(HotelOffer::totalPrice))
            .toList();
    }

    public Booking createBooking(String hotelId, String checkIn, int nights, int guests, String guestName) {
        Objects.requireNonNull(hotelId, "hotelId");
        Objects.requireNonNull(checkIn, "checkIn");
        Objects.requireNonNull(guestName, "guestName");

        LocalDate.parse(checkIn);

        Hotel hotel = inventory.stream()
            .filter(h -> h.id().equalsIgnoreCase(hotelId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown hotelId: " + hotelId));

        if (hotel.maxGuests() < guests) {
            throw new IllegalArgumentException("Guest count exceeds hotel maxGuests");
        }

        HotelOffer offer = toOffer(hotel, nights, guests);

        return new Booking(
            "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            hotel.id(),
            hotel.name(),
            hotel.city(),
            checkIn,
            nights,
            guests,
            guestName,
            offer.totalPrice(),
            "CONFIRMED"
        );
    }

    private HotelOffer toOffer(Hotel hotel, int nights, int guests) {
        int perNight = hotel.basePricePerNight() + Math.max(0, guests - 1) * 25;
        int total = Math.max(1, nights) * perNight;
        return new HotelOffer(hotel.id(), hotel.name(), hotel.city(), perNight, total, hotel.maxGuests());
    }

    public record Hotel(String id, String name, String city, int basePricePerNight, int maxGuests) {
    }

    public record HotelOffer(
        String hotelId,
        String hotelName,
        String city,
        int pricePerNight,
        int totalPrice,
        int maxGuests
    ) {
    }

    public record Booking(
        String bookingId,
        String hotelId,
        String hotelName,
        String city,
        String checkIn,
        int nights,
        int guests,
        String guestName,
        int totalPrice,
        String status
    ) {
    }
}
