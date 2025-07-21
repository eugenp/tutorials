package com.baeldung.spring.modulith.cqrs.movie.seating.internal;

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

@Entity
class ScreenRoom {

    @Id
    @GeneratedValue
    private Long roomId;
    private Long movieId;
    private String movieName;
    private Instant startTime;

    @ElementCollection
    @CollectionTable(name = "screen_room_free_seats", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "seat_number")
    private List<String> freeSeats = allSeats();

    @ElementCollection
    @CollectionTable(name = "screen_room_occupied_seats", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "seat_number")
    private List<String> occupiedSeats = new ArrayList<>();

    public ScreenRoom(Long movieId, String movieName, Instant startTime) {
        this.movieId = movieId;
        this.movieName = movieName;
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
        List<Integer> rows = IntStream.range(0, 20)
            .boxed()
            .toList();

        return IntStream.rangeClosed('A', 'J')
            .mapToObj(c -> String.valueOf((char) c))
            .flatMap(col -> rows.stream()
                .map(row -> col + row))
            .sorted()
            .toList();
    }

    protected ScreenRoom() {
        // Default constructor for JPA
    }

    public Instant getStartTime() {
        return startTime;
    }

    public String getMovieName() {
        return movieName;
    }

    public Long getMovieId() {
        return movieId;
    }

    public List<String> getFreeSeats() {
        return List.copyOf(freeSeats);
    }
}

