package com.baeldung.hexagonalarchitecture.business.entities;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Concert {
    private final UUID id;
    private final String name;
    private final String venue;
    private final String artistName;
    private final ZonedDateTime eventTime;
    private final int maxAttendance;
    private List<Ticket> ticketList;


    @JsonCreator
    public Concert(@JsonProperty("name") final String name, @JsonProperty("venue") final String venue,
        @JsonProperty("artistName") final String artistName, @JsonProperty("eventTime") final ZonedDateTime eventTime,
        @JsonProperty("maxAttendance") final int maxAttendance) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.venue = venue;
        this.artistName = artistName;
        this.eventTime = eventTime;
        this.maxAttendance = maxAttendance;
        this.ticketList = new ArrayList<>();
    }

    public void addTicket(final Ticket ticket) {
        validateTicketSales();
        validateTicket(ticket);
        ticketList.add(ticket);
    }

    public void removeTicket(final UUID id) {
        final Ticket ticket = getTicket(id);
        ticketList.remove(ticket);
    }

    private Ticket getTicket(final UUID id) {
        return ticketList.stream()
            .filter(ticket -> ticket.getId()
                .equals(id))
            .findFirst()
            .orElseThrow(() -> new EntityException("Ticket with ID: '" + id + "' does not exist."));
    }

    private void validateTicketSales() {
        if (ticketList.size() == maxAttendance) {
            throw new EntityException("Concert is sold out.");
        }
    }

    private void validateTicket(final Ticket ticket) {
        if (ticket == null) {
            throw new EntityException("The ticket cannot be null.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public String getArtistName() {
        return artistName;
    }

    public ZonedDateTime getEventTime() {
        return eventTime;
    }

    public int getMaxAttendance() {
        return maxAttendance;
    }

    @JsonIgnore
    public List<Ticket> getTicketList() {
        return Collections.unmodifiableList(ticketList);
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concert)) {
            return false;
        }
        final Concert concert = (Concert) o;
        return maxAttendance == concert.maxAttendance &&
            Objects.equals(id, concert.id) &&
            Objects.equals(name, concert.name) &&
            Objects.equals(venue, concert.venue) &&
            Objects.equals(artistName, concert.artistName) &&
            Objects.equals(eventTime, concert.eventTime) &&
            Objects.equals(ticketList, concert.ticketList);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, name, venue, artistName, eventTime, maxAttendance, ticketList);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Concert.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("name='" + name + "'")
            .add("venue='" + venue + "'")
            .add("artistName='" + artistName + "'")
            .add("eventTime=" + eventTime)
            .add("maxAttendance=" + maxAttendance)
            .add("ticketList=" + ticketList)
            .toString();
    }
}
