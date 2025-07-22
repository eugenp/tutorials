package com.baeldung.spring.modulith.cqrs.movie.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(schema = "read_storage")
class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String screenRoom;
    private Instant startTime;

    @ElementCollection
    @CollectionTable(name = "screen_room_free_seats", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "seat_number")
    private List<String> freeSeats = allSeats();

    @ElementCollection
    @CollectionTable(name = "screen_room_occupied_seats", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "seat_number")
    private List<String> occupiedSeats = new ArrayList<>();

    public Movie(String movieName, String screenRoom, Instant startTime) {
        this.title = movieName;
        this.screenRoom = screenRoom;
        this.startTime = startTime;
    }

    void occupySeat(String seatNumber) {
        if (freeSeats.contains(seatNumber)) {
            freeSeats.remove(seatNumber);
            occupiedSeats.add(seatNumber);
        } else {
            throw new IllegalArgumentException("Seat " + seatNumber + " is not available.");
        }
    }

    void freeSeat(String seatNumber) {
        if (occupiedSeats.contains(seatNumber)) {
            occupiedSeats.remove(seatNumber);
            freeSeats.add(seatNumber);
        } else {
            throw new IllegalArgumentException("Seat " + seatNumber + " is not currently occupied.");
        }
    }

    static List<String> allSeats() {
        List<Integer> rows = IntStream.range(1, 20)
            .boxed()
            .toList();

        return IntStream.rangeClosed('A', 'J')
            .mapToObj(c -> String.valueOf((char) c))
            .flatMap(col -> rows.stream()
                .map(row -> col + row))
            .sorted()
            .toList();
    }

    protected Movie() {
        // Default constructor for JPA
    }

    public Instant startTime() {
        return startTime;
    }

    public String title() {
        return title;
    }

    public String screenRoom() {
        return screenRoom;
    }

    public List<String> freeSeats() {
        return List.copyOf(freeSeats);
    }

    public List<String> occupiedSeatsSeats() {
        return List.copyOf(freeSeats);
    }
}

